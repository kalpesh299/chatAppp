package com.example.chatAppp.controller;

import com.example.chatAppp.dao.statusRepository;
import com.example.chatAppp.dao.userRepository;
import com.example.chatAppp.model.ChatHistory;
import com.example.chatAppp.model.Status;
import com.example.chatAppp.model.Users;
import com.example.chatAppp.service.chatHistoryService;
import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("api/v1/chat")
public class ChatHistroryController {

    @Autowired
    chatHistoryService chatservice;

    @Autowired
    userRepository userrepository;

    @Autowired
    statusRepository statusrepository;

    @PostMapping("send-msg")
    public ResponseEntity<String>sendMessage(@RequestBody String requestData){
        JSONObject requestObj=new JSONObject(requestData);
        JSONObject errorList=validateRequest(requestObj);
        if(errorList.keySet().isEmpty()){
            ChatHistory chat=setChat(requestObj);
            int chatid=chatservice.savechat(chat);
            return new ResponseEntity<>("msg sent"+chatid, HttpStatus.CREATED);

        }else{
            return new ResponseEntity<>(errorList.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-chat")
    public ResponseEntity<String>getChatsByUserId(@Nullable @RequestParam Integer senderId){
        JSONObject response=chatservice.getChatsByUserId(senderId);
        return new ResponseEntity<>(response.toString(),HttpStatus.OK);

    }

    private ChatHistory setChat(JSONObject requestObj) {
        ChatHistory chat=new ChatHistory();
        Users sender_=userrepository.findById(requestObj.getInt("sender")).get();
        Users reciver=userrepository.findById(requestObj.getInt("reciiver")).get();
        Users sender=new Users();
        chat.setReciver(reciver);
        chat.setSender(sender_);
        chat.setMessage(requestObj.getString("message"));
        sender.setCreatedDate(sender_.getCreatedDate());
        Timestamp createdtime=new Timestamp(System.currentTimeMillis());
        chat.setCreatedDate(createdtime);
        Status status=statusrepository.findById(1).get();
//        Status status=statusrepository.findById(requestObj.getInt("statusId")).get();
        sender.setStatusId(status);




        return chat;
    }

    private JSONObject validateRequest(JSONObject data){
        JSONObject errorObj=new JSONObject();
        if(!data.has("sender")){
errorObj.put("sender","missing parameter");
        }
        if(!data.has("reciiver")){
            errorObj.put("reciiver","missing parameter");
        }
        if(data.has("message")){
            if (data.getString("message").isBlank() || data.getString("message").isEmpty()){
                errorObj.put("message","message cannot be blank or empty");
            }

        }else{
            errorObj.put("message","missing parameter");
        }
        return errorObj;
    }
}

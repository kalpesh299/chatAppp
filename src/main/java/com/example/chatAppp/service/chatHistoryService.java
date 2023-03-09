package com.example.chatAppp.service;

import com.example.chatAppp.dao.chatRepository;
import com.example.chatAppp.model.ChatHistory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class chatHistoryService {
    @Autowired
    chatRepository chatrepository;
    public int savechat(ChatHistory chat) {
        return chatrepository.save(chat).getChatId();
    }

    public JSONObject getChatsByUserId(Integer senderId) {
     List<ChatHistory> chatHistoryList=chatrepository.getChatsByUserId(senderId);
//       List<ChatHistory> chatHistoryList=chatrepository.findById(senderId).get();
         JSONObject response=new JSONObject();
         if(!chatHistoryList.isEmpty()){
             response.put("senderId",chatHistoryList.get(0).getSender().getUserId());
             response.put("sendername",chatHistoryList.get(0).getSender().getUserName());
         }
        JSONArray recivers=new JSONArray();
         for(ChatHistory chat:chatHistoryList){
             JSONObject chatObj=new JSONObject();
             chatObj.put("reciverId",chat.getReciver().getUserId());
             chatObj.put("reciverName",chat.getReciver().getFirstName());
             chatObj.put("message",chat.getMessage());
             recivers.put(chatObj);
         }
         response.put("reciver",recivers);
         return response;
    }
}

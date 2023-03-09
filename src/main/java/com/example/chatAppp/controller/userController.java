package com.example.chatAppp.controller;

import com.example.chatAppp.dao.statusRepository;
import com.example.chatAppp.dao.userRepository;
import com.example.chatAppp.model.Status;
import com.example.chatAppp.model.Users;
import com.example.chatAppp.service.userService;
import com.example.chatAppp.util.CommonUtils;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class userController {
    @Autowired
    userService userservice;

    @Autowired
    statusRepository statusrepository;

    @Autowired
    userRepository userrepository;
    @PostMapping("createuser")
    public ResponseEntity<String>addUser(@RequestBody String userData){

        JSONObject isvalid=validationUserRequest(userData);
        Users user=null;
        if(isvalid.keySet().isEmpty()){
             user=setUser(userData);
        }else{return new ResponseEntity<>(isvalid.toString(),HttpStatus.BAD_REQUEST);}
      int id=userservice.addUser(user);


//        return new ResponseEntity<>("user created with Id", HttpStatus.CREATED);
        return new ResponseEntity<>("user created with Id"+id, HttpStatus.CREATED);
    }


    @GetMapping("getusers")
    public ResponseEntity<String>getUsers(@Nullable @RequestParam String userId ){

     JSONArray userArr=userservice.getUsers(userId);
     return new ResponseEntity<>(userArr.toString(),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestBody String requestData){
        JSONObject jsonobj=new JSONObject(requestData);
        JSONObject isvalidjson=validateLogin(jsonobj);
        if(isvalidjson.keySet().isEmpty()){
            String username=jsonobj.getString("username");
            String password= jsonobj.getString("password");
           JSONObject userobject= userservice.login(username,password);
           if(userobject.has("errorMessage")){
               return new ResponseEntity<>(userobject.toString(),HttpStatus.BAD_REQUEST);
           }else{
               return new ResponseEntity<>(userobject.toString(),HttpStatus.OK);
           }
        }else{
            return new ResponseEntity<>(isvalidjson.toString(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("delete-user/{userid}")
    public ResponseEntity<String>updateUser(@PathVariable String userid,@RequestBody String user){

         JSONObject jsonObj=validationUserRequest(user);
        Users newuser=null;
         if(jsonObj.keySet().isEmpty()){
             newuser=setUser(user);
            JSONObject obj= userservice.updateUser(newuser,userid);
            if(obj.has("errorMassage")){
                return new ResponseEntity<>(obj.toString(),HttpStatus.BAD_REQUEST);
            }
         }else{
             return new ResponseEntity<>(jsonObj.toString(),HttpStatus.BAD_REQUEST);
         }


        return new ResponseEntity<>("user updated with id"+userid,HttpStatus.OK);
    }

    private JSONObject validateLogin(JSONObject json) {

        JSONObject errorList=new JSONObject();
       if(!json.has("username")){
           errorList.put("username","missing username");
       }
       if(!json.has("password")){
           errorList.put("password","missing password");
       }
       return errorList;
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<String>deleteUser(@PathVariable int id){
        userservice.deleteUser(id);
        return new ResponseEntity<>("user deleted"+id,HttpStatus.OK);
    }


    private JSONObject validationUserRequest(String userData){
        JSONObject userJson=new JSONObject(userData);
        JSONObject errorList=new JSONObject();
//        JSONArray missingParam=new JSONArray();
        if(userJson.has("userName")){
             String userName=userJson.getString("userName");
            List<Users>userList=userrepository.findByUserName(userName);
            if(!userList.isEmpty()){
                errorList.put("userName","username already exist");
                return errorList;
            }

        }else{
  errorList.put("userName","missing Parameter");
        }
        if(userJson.has("password")){
            String password=userJson.getString("password");
            if(!CommonUtils.isValidPassword(password)){
                errorList.put("password","please enter valid password");
            }
        }else{
            errorList.put("password","missing Parameter");
        }
        if(userJson.has("email")){
            String email=userJson.getString("email");
            if(!CommonUtils.isValidEmail(email)){
                errorList.put("email","please enter vaid email");
            }
        }else{
            errorList.put("email","missing Parameter");
        }
        if(userJson.has("phoneNumber")){
            String phoneNumber=userJson.getString("phoneNumber");
            if(!CommonUtils.isValidPhoneNumber(phoneNumber)){

            }
        }else{
            errorList.put("phoneNumber","missing Parameter");
        }
        if(userJson.has("firstName")){
            String firstName=userJson.getString("firstName");
        }else{
            errorList.put("firstName","mising Parameter");
        }


//        if(userJson.has("age")){
//            String age=userJson.getString("age");
//        }
//        if(userJson.has("lastName")){
//            String lastName=userJson.getString("lastName");
//        }
       return errorList;
    }
    private Users setUser(String _user) {
        JSONObject json=new JSONObject(_user);
        Users user=new Users();
        user.setUserName(json.getString("userName"));
        user.setPassword(json.getString("password"));
        user.setEmail(json.getString("email"));
        user.setPhoneNumber(json.getString("phoneNumber"));
        user.setFirstName(json.getString("firstName"));

        if(json.has("age")){
            user.setAge(json.getInt("age"));
        }
        if(json.has("lastName")){
            user.setLastName(json.getString("lastName"));
        }




        Timestamp curtime=new Timestamp(System.currentTimeMillis());
        user.setCreatedDate(curtime);
//        Timestamp curuptime=new Timestamp(System.currentTimeMillis());
//        user.setUpdatedDate(curuptime);
        Status status=statusrepository.findById(json.getInt("statusId")).get();
        user.setStatusId(status);

//        return user;
        return user;


    }
}

package com.example.chatAppp.service;

import com.example.chatAppp.dao.userRepository;
import com.example.chatAppp.model.Users;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    userRepository userrepository;

    public int addUser(Users user) {
        return userrepository.save(user).getUserId();
    }

    public JSONArray getUsers(String id) {
        JSONArray response = new JSONArray();

        if (id != null) {
            List<Users> usersList = userrepository.getUserbyUserId(Integer.valueOf(id));
            for (Users user : usersList) {
                JSONObject userJson = createResponse(user);
                response.put(userJson);

            }
        } else {
            List<Users> userList = userrepository.getAllUsers();
            for (Users user : userList) {
                JSONObject userJson = createResponse(user);
                response.put(userJson);

            }
        }
        return response;
    }


    public JSONObject createResponse(Users user) {
        JSONObject json = new JSONObject();
        json.put("userId", user.getUserId());
        json.put("userName", user.getUserName());
        json.put("password", user.getPassword());
        json.put("email", user.getEmail());
        json.put("phoneNumber", user.getPhoneNumber());
        json.put("firstName", user.getFirstName());
        json.put(" lastName", user.getLastName());
        json.put("age", user.getAge());
        json.put("createdDate", user.getCreatedDate());
        return json;
    }

    public void deleteUser(Integer id) {
//        userrepository.deleteById(id);
        userrepository.deleteUserById(id);
    }

    public JSONObject login(String username, String password) {
        JSONObject response = new JSONObject();
        List<Users> user = userrepository.findByUserName(username);
        if (user.isEmpty()) {
            response.put("errorMessage", "user not exist");
//            return response;
        } else {
            Users userObj = user.get(0);
            if (password.equals(userObj.getPassword())) {
                response = createResponse(userObj);
            } else {
                response.put("errorMessage", "password is not valid");
            }

        }
        return response;
    }

    public JSONObject updateUser(Users newuser, String id) {
        List<Users> userList = userrepository.getUserbyUserId(Integer.valueOf(id));
        JSONObject errorobject = new JSONObject();
        if (!userList.isEmpty()) {
            Users presentuser = userList.get(0);
            newuser.setUserId(presentuser.getUserId());
            newuser.setCreatedDate(presentuser.getCreatedDate());
            newuser.setPassword(presentuser.getPassword());
            userrepository.save(newuser);
        } else {
            errorobject.put("errorMassage", "user dosenot exist");
        }

        return errorobject;

    }
}

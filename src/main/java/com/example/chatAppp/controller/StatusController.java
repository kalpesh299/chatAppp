package com.example.chatAppp.controller;

import com.example.chatAppp.model.Status;
import com.example.chatAppp.service.statusService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/status")
public class StatusController {

    @Autowired
    statusService statusservice;
    @PostMapping("addstatus")
    public ResponseEntity<String>createStatus(@RequestBody String status){
        Status newstatus=setStatus(status);
        int id=statusservice.createStatus(newstatus);
        return new ResponseEntity<>("status is save with id"+id, HttpStatus.CREATED);
    }

    private Status setStatus(String _status) {
        Status status=new Status();
        JSONObject json=new JSONObject(_status);
        status.setStatusName(json.getString("statusName"));
        status.setStatusDescription(json.getString("statusDescription"));
        return status;
    }
}

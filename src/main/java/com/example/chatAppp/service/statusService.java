package com.example.chatAppp.service;

import com.example.chatAppp.dao.statusRepository;
import com.example.chatAppp.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class statusService {
    @Autowired
    statusRepository statusrepository;
    public int createStatus(Status newstatus) {
   return statusrepository.save(newstatus).getStatusId();
    }
}

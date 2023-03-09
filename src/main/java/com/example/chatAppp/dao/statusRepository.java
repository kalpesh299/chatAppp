package com.example.chatAppp.dao;

import com.example.chatAppp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface statusRepository extends JpaRepository<Status,Integer> {

}

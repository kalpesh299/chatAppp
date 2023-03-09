package com.example.chatAppp.dao;

import com.example.chatAppp.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface chatRepository extends JpaRepository<ChatHistory,Integer> {

    @Query(value = "Select * from tbl_chat_chathistory where sender_id = :senderId and status_id = 1",
            nativeQuery = true)
    List<ChatHistory> getChatsByUserId(Integer senderId);


}

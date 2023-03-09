package com.example.chatAppp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_chat_chathistory")
public class ChatHistory {

    @Id
    @Column(name="chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chatId;

    @JoinColumn(name="reciver_id")
    @ManyToOne
    private Users reciver;
    @JoinColumn(name="sender_id")
    @ManyToOne
    private Users sender;
    @Column(name="message")

    private String message;


    @Column(name="created_time")
    private Timestamp createdDate;

    @Column(name="updated_time")
    private Timestamp updateDate;


    @JoinColumn(name="status_id")
    @ManyToOne
    private Status statusId;

}

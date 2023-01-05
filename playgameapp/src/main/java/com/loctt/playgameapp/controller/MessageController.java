/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.playgameapp.controller;

import com.loctt.playgameapp.model.Message;
import com.loctt.playgameapp.service.MessageService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author loc12345
 */
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    
    @GetMapping("/api/message")
    public ResponseEntity<List<Message>> getAllMessage() {
        List<Message> list = this.messageService.getAllMessage();
        return ResponseEntity.ok().body(list);
    }
    
    @PutMapping("/api/message")
    public ResponseEntity<Map<String,String>> setUserName(@RequestBody Map<String,String> data, HttpSession session) {
        session.setAttribute("name", data.get("name"));
        return ResponseEntity.ok().body(data);
    }
    
}

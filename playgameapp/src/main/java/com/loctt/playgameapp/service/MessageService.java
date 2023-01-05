/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.playgameapp.service;

import com.loctt.playgameapp.model.Message;
import com.loctt.playgameapp.repository.MessageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author loc12345
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    
    public void addMessage(Message message) {
       
        this.messageRepository.save(message);
    }
    
    public List<Message> getAllMessage() {
        return this.messageRepository.findAll(
                PageRequest.of(0, 12, Sort.by("id").descending())
        ).toList();
    }
}

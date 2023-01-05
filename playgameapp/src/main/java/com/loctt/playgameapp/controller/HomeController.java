/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loctt.playgameapp.controller;

import com.loctt.playgameapp.model.CurrentUser;
import com.loctt.playgameapp.model.Message;
import com.loctt.playgameapp.model.MessageOutput;
import com.loctt.playgameapp.service.MessageService;
import com.loctt.playgameapp.utils.BadWordChecker;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author loc12345
 */
@Controller
public class HomeController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private CurrentUser currentUser;

    @RequestMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("current_user_name", session.getAttribute("name"));
        return "index";
    }

    @MessageMapping("/chat")
    // Sends the return value of this method to /topic/messages
    @SendTo("/topic/messages")
    public MessageOutput getMessages(MessageOutput dto, SimpMessageHeaderAccessor headerAccessor) {
        
        if (dto.getFrom().isEmpty()) {
            dto.setFrom("Anonymous");
        }
        if (BadWordChecker.checkBadWord(dto.getText())) {
            dto.setText("không được chửi bé ơi!!!");
        }
        this.messageService.addMessage(
                new Message(dto.getFrom(), dto.getText(), 1)
        );
        return dto;
    }

}

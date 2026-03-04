package com.edusmart.controller;

import com.edusmart.model.ChatMessage;
import com.edusmart.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage message) {
        ChatMessage saved = chatService.saveMessage(message);
        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(),
                "/queue/messages",
                saved
        );
        messagingTemplate.convertAndSendToUser(
                message.getSenderId(),
                "/queue/messages",
                saved
        );
    }

    @GetMapping("/api/chat/{userId1}/{userId2}")
    public List<ChatMessage> getConversation(
            @PathVariable String userId1,
            @PathVariable String userId2
    ) {
        return chatService.getConversation(userId1, userId2);
    }
}
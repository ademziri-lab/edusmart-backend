package com.edusmart.service;

import com.edusmart.model.ChatMessage;
import com.edusmart.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        String conversationId = generateConversationId(message.getSenderId(), message.getReceiverId());
        message.setConversationId(conversationId);
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getConversation(String userId1, String userId2) {
        String conversationId = generateConversationId(userId1, userId2);
        return chatMessageRepository.findByConversationIdOrderByTimestampAsc(conversationId);
    }

    private String generateConversationId(String userId1, String userId2) {
        if (userId1.compareTo(userId2) < 0) {
            return userId1 + "_" + userId2;
        }
        return userId2 + "_" + userId1;
    }
}
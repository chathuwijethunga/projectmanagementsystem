package com.chathurya.service;

import com.chathurya.modal.Chat;
import com.chathurya.repository.ChatRepository;
import org.springframework.stereotype.Service;


@Service

public class ChatServiceImpl implements ChatService {
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}

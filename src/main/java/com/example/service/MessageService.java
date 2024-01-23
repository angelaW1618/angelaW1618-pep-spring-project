package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isEmpty()
                || message.getMessage_text().length() > 255 || message.getPosted_by() <= 0
                || !accountRepository.existsById(message.getPosted_by())) {
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int message_id) {
        return messageRepository.findById(message_id);
    }

    public int deleteMessage(int messageId) {
        boolean exists = messageRepository.existsById(messageId);
        if (exists) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int updateMessage(int message_id, String newText) {

        if (newText == null || newText.isEmpty() || newText.length() > 255 || !messageRepository.findById(message_id).isPresent()) {
            return 0;
        } else {
            return messageRepository.updateMessageTextById(message_id, newText);
        }

        

    }

    public List<Message> getMessagesByAccountId(int accountId) {
        if (accountId <= 0) {
            return null;
        }
        return messageRepository.findByPostedBy(accountId);
    }

}

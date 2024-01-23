package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.MessageDTO;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController

public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account newAccount = accountService.registerAccount(account);
        if (newAccount != null) {
            return ResponseEntity.ok(newAccount);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<Account>> login(@RequestBody Account account) {
        Optional<Account> loggedInAccount = accountService.login(account.getUsername(), account.getPassword());
        if (loggedInAccount.isPresent()) {
            return ResponseEntity.ok(loggedInAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        if (newMessage != null) {
            return ResponseEntity.ok(newMessage);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        if (message.isPresent()) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        int rowsUpdated = messageService.deleteMessage(messageId);
        if (rowsUpdated == 0) {
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.ok().body(rowsUpdated);
    }

 
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody MessageDTO messageDto) {
        String newText = messageDto.getMessageText();
        int rowsUpdated = messageService.updateMessage(messageId, newText);
        
        if (rowsUpdated == 1) {
            return ResponseEntity.ok().body(rowsUpdated);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.ok(messages);
    }

}

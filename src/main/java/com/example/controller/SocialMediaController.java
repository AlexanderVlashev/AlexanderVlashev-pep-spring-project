package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        
        if (accountService.accountExists(account.getUsername())){
            return ResponseEntity.status(409).body(null);
        }

        Account newAccount = accountService.registerAccount(account);
        if(newAccount == null){
            return ResponseEntity.status(400).body(null);
        } else {
            return ResponseEntity.status(200).body(newAccount);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account checkedAccount = accountService.logintoAccount(account);
        if(checkedAccount == null){
            return ResponseEntity.status(401).body(null);
        } else {
            return ResponseEntity.status(200).body(checkedAccount);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        if(accountService.accountExists(message.getPostedBy())){
            return ResponseEntity.status(400).body(null);
        }
        
        Message createdMessage = messageService.createMessage(message);
        if(createdMessage == null){
            return ResponseEntity.status(400).body(null);
        } else {
            return ResponseEntity.status(200).body(createdMessage);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageByID(@PathVariable Integer message_id){
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Long> deleteMessageByID(@PathVariable Integer message_id){
        return ResponseEntity.status(200).body(messageService.deleteMessageById(message_id));
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Long> updateMessagebyID(@PathVariable Integer message_id, @RequestBody String messageText){
        Long change = messageService.updateMessage(message_id, messageText);
    
        if(change == 0){
            return ResponseEntity.status(400).body(null);
        } else {
            return ResponseEntity.status(200).body(change);
        }
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessageByUser(@PathVariable Integer account_id){
        return ResponseEntity.status(200).body(messageService.getMessageByUser(account_id));
    }

}

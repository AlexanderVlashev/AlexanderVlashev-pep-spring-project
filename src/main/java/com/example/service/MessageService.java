package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message createMessage(Message message){
        if(message.getMessageText().length() > 255 || message.getMessageText().length() == 0){
            return null;
        }

        messageRepository.InsertMessageIntoTable(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch());
        return messageRepository.findByPostedByAndMessageTextAndTimePostedEpoch(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch()); 
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId){
        return messageRepository.findByMessageId(messageId);
    }

    @Transactional
    public Long deleteMessageById(Integer messageId){
        if(messageRepository.findByMessageId(messageId) == null){
            return 0L;
        }
        return messageRepository.deleteByMessageId(messageId);
    }

    @Transactional
    public Long updateMessage(Integer messageId, String MessageText){
        if(messageRepository.findByMessageId(messageId) == null){
            return 0L;
        }
        
        if (MessageText.length() > 255 || MessageText.length() == 0){
            return 0L;
        }

        Message message = messageRepository.findByMessageId(messageId);
        message.setMessageText(MessageText);
        messageRepository.save(message);
        return 1L;
    }

    public List<Message> getMessageByUser(Integer accountId){
        return messageRepository.findAllByPostedBy(accountId);
    }
}

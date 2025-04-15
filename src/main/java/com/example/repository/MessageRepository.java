package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying
    @Query(value = "INSERT INTO message (postedBy, messageText, timePostedEpoch) VALUES (:postedBy, :messageText, :timePostedEpoch)", nativeQuery = true)
    void InsertMessageIntoTable(@Param("postedBy") Integer postedBy,  @Param("messageText") String messageText, @Param("timePostedEpoch") Long timePostedEpoch);
    
    Message findByPostedByAndMessageTextAndTimePostedEpoch(Integer postedBy, String messageText, Long timePostedEpoch);

    List<Message> findAll();

    Message findByMessageId(Integer messageId);

    Long deleteByMessageId(Integer messageId);

    List<Message> findAllByPostedBy(Integer postedBy);
}

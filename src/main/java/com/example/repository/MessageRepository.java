package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.posted_by = :postedBy")
    List<Message> findByPostedBy(@Param("postedBy") Integer postedBy);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.message_text = :newText WHERE m.message_id = :messageId")
    int updateMessageTextById(@Param("messageId") int messageId, @Param("newText") String newText);

}

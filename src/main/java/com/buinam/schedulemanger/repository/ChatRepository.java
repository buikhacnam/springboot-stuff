package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT * FROM message m WHERE m.user_one = ?1 AND m.user_two = ?2 OR m.user_one = ?2 AND m.user_two = ?1", nativeQuery = true)
    Chat findExistedChat(String senderName, String receiverName);
}

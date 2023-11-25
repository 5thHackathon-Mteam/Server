package com.example.hackathon.domain.friend.repository;

import com.example.hackathon.domain.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT f FROM Friend f "
            + "WHERE f.fromUserEmail = :fromUserEmail "
            + "AND f.toUserEmail = :toUserEmail")
    Friend getFriendRequest(
            @Param("fromUserEmail") String fromUserEmail,
            @Param("toUserEmail") String toUserEmail);

}

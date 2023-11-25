package com.example.hackathon.domain.friend.repository;

import com.example.hackathon.domain.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}

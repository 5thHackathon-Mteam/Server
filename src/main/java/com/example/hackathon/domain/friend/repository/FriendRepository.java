package com.example.hackathon.domain.friend.repository;

import com.example.hackathon.domain.friend.domain.Friend;
import com.example.hackathon.domain.member.domain.Member;
import java.util.List;
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

    @Query("SELECT CASE WHEN " +
            "(SELECT COUNT(f) FROM Friend f WHERE f.fromUserEmail = :fromUserEmail AND f.toUserEmail = :toUserEmail AND f.areWeFriend = true) > 0 " +
            "AND " +
            "(SELECT COUNT(f) FROM Friend f WHERE f.fromUserEmail = :toUserEmail AND f.toUserEmail = :fromUserEmail AND f.areWeFriend = true) > 0 " +
            "THEN true ELSE false END")
    boolean allReadyFriend(@Param("fromUserEmail") String currentEmail,
                        @Param("toUserEmail") String toUserEmail);


    @Query("SELECT m FROM Member m WHERE m.email IN (" +
            "SELECT f.fromUserEmail FROM Friend f WHERE f.toUserEmail = :currentEmail AND f.areWeFriend = true) " +
            "OR m.email IN (" +
            "SELECT f.toUserEmail FROM Friend f WHERE f.fromUserEmail = :currentEmail AND f.areWeFriend = true)")
    List<Member> findMutualFriends(@Param("currentEmail") String currentEmail);


}

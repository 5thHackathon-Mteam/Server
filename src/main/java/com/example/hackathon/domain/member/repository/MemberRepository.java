package com.example.hackathon.domain.member.repository;

import com.example.hackathon.domain.member.domain.Member;
import com.example.hackathon.domain.member.dto.MemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByEmail(String email);
}
package com.example.hackathon.domain.friend.service;

import static com.example.hackathon.global.error.exception.ErrorCode.ALREADY_FRIEND;

import com.example.hackathon.domain.friend.domain.Friend;
import com.example.hackathon.domain.friend.dto.CreateFriendRequest;
import com.example.hackathon.domain.friend.dto.FriendListResponse;
import com.example.hackathon.domain.friend.dto.FriendRequest;
import com.example.hackathon.domain.friend.dto.MemberDto;
import com.example.hackathon.domain.friend.repository.FriendRepository;
import com.example.hackathon.domain.member.domain.Member;
import com.example.hackathon.global.error.exception.CustomException;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    public void requestFriend(CreateFriendRequest createFriendRequest) {
        String currentEmail = createFriendRequest.getCurrentEmail();
        String toUserEmail = createFriendRequest.getTargetEmail();

        validate(currentEmail, toUserEmail);

        Friend currentToTarget = Friend.builder()
                .fromUserEmail(currentEmail)
                .toUserEmail(toUserEmail)
                .areWeFriend(true)
                .build();

        Friend targetToCurrent = Friend.builder()
                .fromUserEmail(toUserEmail)
                .toUserEmail(currentEmail)
                .areWeFriend(false)
                .build();

        friendRepository.saveAll(List.of(currentToTarget, targetToCurrent));
    }


    public void acceptFriend(CreateFriendRequest createFriendRequest) {

        String currentEmail = createFriendRequest.getCurrentEmail();
        String fromEmail = createFriendRequest.getTargetEmail();

        validate(currentEmail, fromEmail);

        Friend friendRequest = friendRepository.getFriendRequest(currentEmail, fromEmail);
        friendRequest.acceptFriend(true);
    }

    private void validate(String currentEmail, String toUserEmail) {
        boolean alreadyFriend = friendRepository.allReadyFriend(currentEmail, toUserEmail);
        if(alreadyFriend){
            throw new CustomException(ALREADY_FRIEND);
        }
    }


    public FriendListResponse getFriendList(FriendRequest friendRequest) {
        String currentMail = friendRequest.getCurrentMail();
        List<Member> mutualFriends = friendRepository.findMutualFriends(currentMail);

        List<MemberDto> memberDtoList = mutualFriends.stream()
                .map(mutualFriend -> MemberDto.builder()
                        .email(mutualFriend.getEmail())
                        .build())
                .toList();

        return FriendListResponse.builder()
                .friends(memberDtoList)
                .build();
    }
}

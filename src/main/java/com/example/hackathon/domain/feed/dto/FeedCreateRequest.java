package com.example.hackathon.domain.feed.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
public class FeedCreateRequest {
    private List<MultipartFile> multipartFiles;
    private String content;
}

package com.example.hackathon.domain.feed.service;

import com.example.hackathon.domain.feed.domain.Category;
import com.example.hackathon.domain.feed.domain.Feed;
import com.example.hackathon.domain.feed.dto.FeedCreateRequest;
import com.example.hackathon.domain.feed.dto.FeedResponse;
import com.example.hackathon.domain.feed.repository.FeedImageRepository;
import com.example.hackathon.domain.feed.repository.FeedRepository;
import com.example.hackathon.global.config.S3Uploader;
import com.example.hackathon.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.hackathon.global.error.exception.ErrorCode.CONNECT_S3_ERROR;
import static com.example.hackathon.global.error.exception.ErrorCode.FEED_IMAGE_INVALID_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final S3Uploader s3Uploader;
    private final FeedImageService feedImageService;
    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;

    public void create(FeedCreateRequest request) {
        validate(request);

        String content = request.getContent();
        // ToDo GPT API 이용, get gptContent

        Feed buildFeed = Feed.builder()
                .content(content)
                .gptContent("gptContent")
                .category(Category.NONE)
                .build();

        feedRepository.save(buildFeed);

        List<String> imageUrls = getImageUrls(request);

        imageUrls.stream().map(imageUrl -> feedImageService.from(buildFeed, imageUrl))
                .forEach(feedImageRepository::save);
    }

    public void validate(FeedCreateRequest request){
        List<MultipartFile> multipartFiles = request.getMultipartFiles();
        if (multipartFiles.size() != 4) {
            throw new CustomException(FEED_IMAGE_INVALID_SIZE);
        }
    }

    public List<String> getImageUrls(FeedCreateRequest request) {
        List<MultipartFile> multipartFiles = request.getMultipartFiles();
        try {
            return s3Uploader.uploadMultipartFiles(multipartFiles);
        } catch (IOException e) {
            throw new CustomException(CONNECT_S3_ERROR);
        }
    }

    public List<FeedResponse> getFeedList(Long cursorId, int pageSize) {
//        return null;
        return feedRepository.findPageByCursorId(cursorId, pageSize);
    }
}

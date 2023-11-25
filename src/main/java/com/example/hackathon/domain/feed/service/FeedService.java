package com.example.hackathon.domain.feed.service;

import static com.example.hackathon.global.error.exception.ErrorCode.CONNECT_S3_ERROR;
import static com.example.hackathon.global.error.exception.ErrorCode.FEED_IMAGE_INVALID_SIZE;

import com.example.hackathon.domain.feed.domain.Category;
import com.example.hackathon.domain.feed.domain.Feed;
import com.example.hackathon.domain.feed.domain.FeedImage;
import com.example.hackathon.domain.feed.dto.FeedCreateRequest;
import com.example.hackathon.domain.feed.repository.FeedRepository;
import com.example.hackathon.global.config.S3Uploader;
import com.example.hackathon.global.error.exception.CustomException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final S3Uploader s3Uploader;
    private final FeedImageService feedImageService;
    private final FeedRepository feedRepository;

    public void create(FeedCreateRequest request) {
        validate(request);
        List<String> imageUrls = getImageUrls(request);
        List<FeedImage> feedImages = imageUrls.stream()
                .map(feedImageService::from).toList();

        String content = request.getContent();
        // ToDo GPT API 이용, get gptContent

        Feed buildFeed = Feed.builder()
                .content(content)
                .gptContent("gptContent")
                .category(Category.NONE)
                .imageUrlList(feedImages)
                .build();

        feedRepository.save(buildFeed);
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
}

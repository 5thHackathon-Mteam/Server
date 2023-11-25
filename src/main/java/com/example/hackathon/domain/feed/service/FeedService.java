package com.example.hackathon.domain.feed.service;

import com.example.hackathon.domain.feed.domain.Feed;
import com.example.hackathon.domain.feed.domain.FeedImage;
import com.example.hackathon.domain.feed.dto.FeedCreateRequest;
import com.example.hackathon.domain.feed.dto.FeedImageResponse;
import com.example.hackathon.domain.feed.dto.FeedResponse;
import com.example.hackathon.domain.feed.dto.FeedUpdateRequest;
import com.example.hackathon.domain.feed.openai.OpenAiService;
import com.example.hackathon.domain.feed.repository.FeedImageRepository;
import com.example.hackathon.domain.feed.repository.FeedRepository;
import com.example.hackathon.global.config.S3Uploader;
import com.example.hackathon.global.error.exception.CustomException;
import com.example.hackathon.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.hackathon.global.error.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final S3Uploader s3Uploader;
    private final FeedImageService feedImageService;
    private final FeedRepository feedRepository;
    private final OpenAiService openAiService;
    private final CategoryService categoryService;
    private final FeedImageRepository feedImageRepository;

    public void create(FeedCreateRequest request) {

        String email = SecurityUtil.getCurrentUsername();

        System.out.println("email = " + email);


        validate(request);

        List<String> imageUrls = getImageUrls(request);
        String gptContent = fromContent(imageUrls);
        List<String> categories = request.getCategories();

        Feed buildFeed = Feed.builder()
                .frameColor(request.getFrameColor())
                .gptContent(gptContent)
                .date(request.getDate())
                .build();

        feedRepository.save(buildFeed);

        categories.forEach(category -> categoryService.from(buildFeed, category));

        imageUrls.forEach(imageUrl -> feedImageService.from(buildFeed, imageUrl));
    }

    private String fromContent(List<String> imageUrls) {
        String mainImageUrl = imageUrls.get(0);
        return openAiService.createGptContent(mainImageUrl);
    }

    public void validate(FeedCreateRequest request){
        validateImageSize(request);
        validateCategories(request);
    }

    private void validateCategories(FeedCreateRequest request) {
        List<String> categories = request.getCategories();
        // TODO Catogory 검증
    }

    private void validateImageSize(FeedCreateRequest request) {
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
            System.out.println("e = " + e);
            throw new CustomException(CONNECT_S3_ERROR);
        }
    }

    public List<FeedResponse> getFeedList(Long cursorId, int pageSize) {
        return feedRepository.findPageByCursorId(cursorId, pageSize);
    }

    public FeedResponse updateFeed(Long feedId, FeedUpdateRequest feedUpdateRequest) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new CustomException(FEED_NOT_FOUND));

        feed.changeGptContent(feedUpdateRequest.gptContent());
        feedRepository.save(feed);
        return FeedResponse.from(feed);
    }

    public Boolean deleteFeed(Long feedId) {
        System.out.println("feedId = " + feedId);

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new CustomException(FEED_NOT_FOUND));

        feed.changeIsDeleted();
        feedRepository.save(feed);

        return true;
    }

    public List<FeedImageResponse> getFeedImages(Long feedId) {
        List<FeedImage> allByFeedId = feedImageRepository.findAllByFeedId(feedId);

        List<FeedImageResponse> feedImageResponses = new ArrayList<>();

        for (FeedImage feedImage : allByFeedId) {
            feedImageResponses.add(new FeedImageResponse(feedImage.getImageUrl()));
        }

        return feedImageResponses;


    }
}

package com.example.hackathon.domain.feed.service;

import com.example.hackathon.domain.feed.domain.Category;
import com.example.hackathon.domain.feed.domain.Feed;
import com.example.hackathon.domain.feed.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void from(Feed feed, String categoryName) {
        Category category = Category.builder()
                .name(categoryName)
                .feed(feed)
                .build();
        categoryRepository.save(category);
    }
}

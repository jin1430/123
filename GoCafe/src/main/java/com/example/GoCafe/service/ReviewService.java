package com.example.GoCafe.service;

import com.example.GoCafe.entity.Review;
import com.example.GoCafe.repository.ReviewRepository;
import com.example.GoCafe.support.EntityIdUtil;
import com.example.GoCafe.support.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    @Transactional(readOnly = true)
    public List<Review> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Review findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found: " + id));
    }

    @Transactional
    public Review create(Review entity) {
        EntityIdUtil.setId(entity, null);
        return repository.save(entity);
    }

    @Transactional
    public Review update(Long id, Review entity) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Review not found: " + id);
        }
        EntityIdUtil.setId(entity, id);
        return repository.save(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Review not found: " + id);
        }
        repository.deleteById(id);
    }
}

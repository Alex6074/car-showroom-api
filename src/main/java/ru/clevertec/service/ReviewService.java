package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.dto.ReviewDto;
import ru.clevertec.entity.Review;
import ru.clevertec.exception.ReviewNotFoundException;
import ru.clevertec.mapper.CarMapper;
import ru.clevertec.mapper.ClientMapper;
import ru.clevertec.mapper.ReviewMapper;
import ru.clevertec.repository.ReviewRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final CarMapper carMapper;
    private final ClientMapper clientMapper;

    @Transactional
    public ReviewDto create(ReviewDto dto) {
        Review review = reviewMapper.toReview(dto);
        return reviewMapper.toReviewDto(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public ReviewDto findById(Long id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toReviewDto)
                .orElseThrow(() -> ReviewNotFoundException.byReviewId(id));
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findAll() {
        return reviewMapper.toReviewDtoList(reviewRepository.findAll());
    }

    @Transactional
    public ReviewDto update(Long id, ReviewDto dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> ReviewNotFoundException.byReviewId(id));

        review.setText(dto.text());
        review.setRating(dto.rating());
        review.setCar(carMapper.toCar(dto.car()));
        review.setClient(clientMapper.toClient(dto.client()));

        return reviewMapper.toReviewDto(reviewRepository.save(review));
    }

    @Transactional
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> searchByRating(int rating) {
        return reviewMapper.toReviewDtoList(reviewRepository.findByRating(rating));
    }
}

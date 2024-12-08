package ru.clevertec.exception;

public class ReviewNotFoundException extends RuntimeException {
    private ReviewNotFoundException(String message) {
        super(message);
    }

    public static ReviewNotFoundException byReviewId(Long id) {
        return new ReviewNotFoundException(
                String.format("Review not found by id %s", id)
        );
    }
}

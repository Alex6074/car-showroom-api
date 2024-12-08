package ru.clevertec.exception;

public class CategoryNotFoundException extends RuntimeException {
    private CategoryNotFoundException(String message) {
        super(message);
    }

    public static CategoryNotFoundException byCategoryId(Long id) {
        return new CategoryNotFoundException(
                String.format("Category not found by id %s", id)
        );
    }
}

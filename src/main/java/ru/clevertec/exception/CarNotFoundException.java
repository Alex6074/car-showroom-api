package ru.clevertec.exception;

public class CarNotFoundException extends RuntimeException {
    private CarNotFoundException(String message) {
        super(message);
    }

    public static CarNotFoundException byCarId(Long id) {
        return new CarNotFoundException(
                String.format("Car not found by id %s", id)
        );
    }
}

package ru.clevertec.exception;

public class CarShowroomNotFoundException extends RuntimeException{
    private CarShowroomNotFoundException(String message) {
        super(message);
    }

    public static CarShowroomNotFoundException byCarShowroomId(Long id) {
        return new CarShowroomNotFoundException(
                String.format("Car showroom not found by id %s", id)
        );
    }
}

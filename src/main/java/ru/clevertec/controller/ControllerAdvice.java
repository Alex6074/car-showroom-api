package ru.clevertec.controller;

import jakarta.validation.ValidationException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.exception.CarNotFoundException;
import ru.clevertec.exception.CarShowroomNotFoundException;
import ru.clevertec.exception.CategoryNotFoundException;
import ru.clevertec.exception.ClientNotFoundException;
import ru.clevertec.exception.ReviewNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionObject response400(@RequestBody Exception e) {
        return createExceptionObject(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            ValidationException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionObject response422(@RequestBody Exception e) {
        return createExceptionObject(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {
            ObjectNotFoundException.class,
            CarNotFoundException.class,
            CarShowroomNotFoundException.class,
            CategoryNotFoundException.class,
            ClientNotFoundException.class,
            ReviewNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionObject response404(@RequestBody Exception e) {
        return createExceptionObject(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ExceptionObject createExceptionObject(String message, HttpStatus status) {
        return new ExceptionObject(status.value(), String.valueOf(status), message);
    }

    public record ExceptionObject(int code, String status, String message) {
    }
}

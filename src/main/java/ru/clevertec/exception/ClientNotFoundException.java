package ru.clevertec.exception;

public class ClientNotFoundException extends RuntimeException {
    private ClientNotFoundException(String message) {
        super(message);
    }

    public static ClientNotFoundException byClientId(Long id) {
        return new ClientNotFoundException(
                String.format("Client not found by id %s", id)
        );
    }
}

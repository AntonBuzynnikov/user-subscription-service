package ru.buzynnikov.user_subscription_service.exceptions;

/**
 * Ошибка, возникающая при попытке получить пользователя по несуществующему идентификатору
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

package ru.buzynnikov.user_subscription_service.exceptions;

/**
 * Ошибка, возникающая при отсутствии подписки
 */
public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}

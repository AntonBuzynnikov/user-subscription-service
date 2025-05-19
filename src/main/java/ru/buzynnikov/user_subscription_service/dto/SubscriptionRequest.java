package ru.buzynnikov.user_subscription_service.dto;


/**
 * DTO для запросов, связанных с подпиской
 * @param id ID существующей подписки
 */
public record SubscriptionRequest(Long id) {
}

package ru.buzynnikov.user_subscription_service.dto;

/**
 * DTO для ответа, связанного с подпиской
 * @param id - id подписки
 * @param name - название подписки
 */
public record SubscriptionResponse(Long id, String name) {
}

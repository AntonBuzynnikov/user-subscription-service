package ru.buzynnikov.user_subscription_service.dto;


import jakarta.validation.constraints.NotNull;

/**
 * DTO для запросов, связанных с подпиской
 * @param id ID существующей подписки
 */
public record SubscriptionRequest(@NotNull(message = "Идентификатор подписки не может быть пустым") Long id) {
}

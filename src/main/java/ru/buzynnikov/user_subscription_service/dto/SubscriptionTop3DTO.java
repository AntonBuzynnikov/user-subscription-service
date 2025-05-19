package ru.buzynnikov.user_subscription_service.dto;

/**
 * DTO для получения 3-х самых популярных подписок из репозитория
 * @param id - id подписки
 * @param name - название подписки
 * @param userId - id пользователя
 */
public record SubscriptionTop3DTO(Long id, String name, Long userId) {
}

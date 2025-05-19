package ru.buzynnikov.user_subscription_service.dto;

/**
 * DTO для ответа, содержащего информацию о пользователе
 * @param id - id пользователя
 * @param name - имя пользователя
 */
public record UserResponse(Long id, String name) {
}

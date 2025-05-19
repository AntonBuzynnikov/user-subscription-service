package ru.buzynnikov.user_subscription_service.dto;

/**
 * DTO для ответа с ошибкой
 * @param message Сообщение об ошибке
 */
public record ErrorResponse(String message) {
}

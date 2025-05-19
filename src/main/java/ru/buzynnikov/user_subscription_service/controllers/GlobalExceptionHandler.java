package ru.buzynnikov.user_subscription_service.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.buzynnikov.user_subscription_service.dto.ErrorResponse;
import ru.buzynnikov.user_subscription_service.exceptions.SubscriptionNotFoundException;
import ru.buzynnikov.user_subscription_service.exceptions.UserNotFoundException;


/**
 * Класс глобального перехватчика исключений, предназначенный для обработки исключительных ситуаций,
 * возникающих в контроллерах приложения.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик исключения {@link DataIntegrityViolationException}, которое возникает при нарушении целостности данных,
     * например, попытке добавить дублирующиеся записи в базу данных.
     *
     * @param ex исключение, вызванное нарушением целостности данных
     * @return HTTP-ответ с ошибкой CONFLICT (код 409) и описанием возникшей ситуации
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Дублирование данных: " + ex.getMostSpecificCause().getMessage()));
    }
    /**
     * Обработчик исключения {@link UserNotFoundException}, которое сигнализирует о том, что запрашиваемый пользователь не найден.
     *
     * @param ex исключение, вызванное отсутствием пользователя
     * @return HTTP-ответ с ошибкой NOT FOUND (код 404) и сообщением о причине отсутствия пользователя
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Обработчик исключения {@link SubscriptionNotFoundException}, которое сигнализирует о том, что запрашиваемая подписка не найдена.
     *
     * @param ex исключение, вызванное отсутствием подписки
     * @return HTTP-ответ с ошибкой NOT FOUND (код 404) и сообщением о причине отсутствия подписки
     */
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }
}

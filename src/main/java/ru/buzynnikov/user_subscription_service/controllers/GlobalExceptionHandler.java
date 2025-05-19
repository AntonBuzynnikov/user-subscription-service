package ru.buzynnikov.user_subscription_service.controllers;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.buzynnikov.user_subscription_service.aspect.ErrorLog;
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
    @ErrorLog
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
    @ErrorLog
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
    @ErrorLog
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    /**
     * Метод-обработчик исключения типа {@link MethodArgumentNotValidException}. Возникает, когда проверка валидности переданных аргументов метода завершилась неудачей.
     *
     * @param ex экземпляр исключения проверки аргументов
     * @return HTTP-ответ с кодом BAD REQUEST (400), содержащий сообщение о первой ошибке валидации
     */
    @ErrorLog
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }
    /**
     * Метод-обработчик исключения типа {@link HttpMessageNotReadableException}. Происходит, когда сервер не смог прочитать тело запроса (например, неверный JSON).
     *
     * @param ex экземпляр исключения чтения тела запроса
     * @return HTTP-ответ с кодом BAD REQUEST (400), содержащий стандартное сообщение об ошибке
     */
    @ErrorLog
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Ошибка в теле запроса"));
    }
}

package ru.buzynnikov.user_subscription_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO для запроса, связанного с пользователем
 * @param name - имя пользователя
 */
public record UserRequest(@NotNull(message = "Имя пользователя не может быть пустым")
                          @NotEmpty(message = "Имя пользователя не может быть пустым")
                          @Size(min = 3, max = 255, message = "Имя пользователя должно быть не менее 3 и не более 255 символов")
                          String name) {

}

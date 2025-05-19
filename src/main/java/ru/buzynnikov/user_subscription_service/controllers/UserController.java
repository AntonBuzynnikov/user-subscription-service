package ru.buzynnikov.user_subscription_service.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionRequest;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse;
import ru.buzynnikov.user_subscription_service.dto.UserRequest;
import ru.buzynnikov.user_subscription_service.dto.UserResponse;
import ru.buzynnikov.user_subscription_service.services.interfaces.UserService;

import java.net.URI;
import java.util.Set;

/**
 * Контроллер для управления пользователями через REST API.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создание нового пользователя.
     *
     * @param request тело запроса с информацией о новом пользователе
     * @param uriBuilder конструктор URI для формирования адреса ресурса
     * @return успешный HTTP-ответ с новым объектом пользователя и ссылкой на созданный ресурс
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request, UriComponentsBuilder uriBuilder) {

        UserResponse userResponse = userService.saveUser(request);
        URI location = uriBuilder.path("/api/v1/users/{id}").buildAndExpand(userResponse.id()).toUri();

        return ResponseEntity.created(location).body(userResponse);
    }

    /**
     * Получение информации о конкретном пользователе по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return успешный HTTP-ответ с объектом пользователя
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Обновление информации о существующем пользователе.
     *
     * @param id      идентификатор пользователя
     * @param request обновленные данные пользователя
     * @return пустой успешный HTTP-ответ (HTTP статус 204 No Content)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        userService.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * Удаление существующего пользователя.
     *
     * @param id идентификатор удаляемого пользователя
     * @return пустой успешный HTTP-ответ (HTTP статус 204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Добавление новой подписки пользователю.
     *
     * @param id          идентификатор пользователя
     * @param request     информация о добавляемой подписке
     * @return пустой успешный HTTP-ответ (HTTP статус 204 No Content)
     */
    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<Void> subscribeUser(@PathVariable Long id,@Valid @RequestBody SubscriptionRequest request) {
        userService.addSubscription(id, request);
        return ResponseEntity.noContent().build();
    }
    /**
     * Получение списка подписок конкретного пользователя.
     *
     * @param id идентификатор пользователя
     * @return успешный HTTP-ответ с набором подписок пользователя
     */
    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<Set<SubscriptionResponse>> getUserSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserSubscriptions(id));
    }

    /**
     * Отмена подписки пользователя на определенный ресурс.
     *
     * @param id              идентификатор пользователя
     * @param subscriptionId  идентификатор отменяемой подписки
     * @return пустой успешный HTTP-ответ (HTTP статус 204 No Content)
     */
    @DeleteMapping("/{id}/subscriptions/{subscriptionId}")
    public ResponseEntity<Void> unsubscribeUser(@PathVariable Long id, @PathVariable Long subscriptionId) {
        userService.deleteSubscriptionFormUser(id, subscriptionId);
        return ResponseEntity.noContent().build();
    }


}

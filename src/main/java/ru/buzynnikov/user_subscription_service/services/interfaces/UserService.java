package ru.buzynnikov.user_subscription_service.services.interfaces;

import ru.buzynnikov.user_subscription_service.dto.SubscriptionRequest;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse;
import ru.buzynnikov.user_subscription_service.dto.UserRequest;
import ru.buzynnikov.user_subscription_service.dto.UserResponse;

import java.util.Set;

/**
 * Интерфейс сервиса для работы с пользователями и управлением их подписками.
 */
public interface UserService {

    /**
     * Создает нового пользователя на основе переданного запроса.
     *
     * @param request объект UserRequest, содержащий информацию о создаваемом пользователе
     * @return объект UserResponse, представляющий сохраненного пользователя
     */
    UserResponse saveUser(UserRequest request);
    /**
     * Возвращает информацию о пользователе по его уникальному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект UserResponse, содержащий информацию о пользователе
     */
    UserResponse getUserById(Long id);
    /**
     * Обновляет информацию о пользователе.
     *
     * @param id      идентификатор пользователя
     * @param request объект UserRequest, содержащий новые данные пользователя
     */
    void updateUser(Long id, UserRequest request);
    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void deleteUser(Long id);
    /**
     * Добавляет новую подписку пользователю.
     *
     * @param userId     идентификатор пользователя
     * @param request    объект SubscriptionRequest, содержащий информацию о подписке
     */
    void addSubscription(Long userId, SubscriptionRequest request);
    /**
     * Возвращает список подписок пользователя.
     *
     * @param userId идентификатор пользователя
     * @return множество объектов SubscriptionResponse, содержащее информацию о подписках пользователя
     */
    Set<SubscriptionResponse> getUserSubscriptions(Long userId);
    /**
     * Удаляет подписку у пользователя.
     *
     * @param userId          идентификатор пользователя
     * @param subscriptionId  идентификатор подписки
     */
    void deleteSubscriptionFormUser(Long userId, Long subscriptionId);
}

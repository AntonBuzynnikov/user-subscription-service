package ru.buzynnikov.user_subscription_service.services.interfaces;

import ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс службы для работы с подписками (Subscription).
 */
public interface SubscriptionService {

    /**
     * Проверяет существование подписки по переданному идентификатору.
     *
     * @param id идентификатор подписки
     */
    void existsSubscription(Long id);

    /**
     * Получает список подписок для указанного пользователя.
     *
     * @param id идентификатор пользователя
     * @return множество объектов SubscriptionResponse, включающих информацию о подписках пользователя
     */
    Set<SubscriptionResponse> getSubscriptionsByUserId(Long id);

    /**
     * Получает список трех самых популярных подписок.
     *
     * @return список объектов SubscriptionResponse, содержащий три самые популярные подписки
     */
    List<SubscriptionResponse> getSubscriptionsTop3();
}

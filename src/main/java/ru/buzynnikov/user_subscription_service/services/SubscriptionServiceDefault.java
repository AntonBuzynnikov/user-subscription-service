package ru.buzynnikov.user_subscription_service.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse;
import ru.buzynnikov.user_subscription_service.exceptions.SubscriptionNotFoundException;
import ru.buzynnikov.user_subscription_service.repositories.SubscriptionRepository;
import ru.buzynnikov.user_subscription_service.services.interfaces.SubscriptionService;

import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса SubscriptionService для работы с подписками.
 */
@Service
public class SubscriptionServiceDefault implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceDefault(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Проверяет существование подписки по её идентификатору.
     * Если подписка не существует, выбрасывается исключение SubscriptionNotFoundException.
     *
     * @param id идентификатор подписки
     */
    @Transactional
    @Override
    public void existsSubscription(Long id) {
        if (!subscriptionRepository.existsById(id)) throw new SubscriptionNotFoundException(String.format( "Подписка c id %d не найдена", id));

    }

    /**
     * Возвращает набор подписок, привязанных к пользователю с указанным идентификатором.
     *
     * @param id идентификатор пользователя
     * @return множество объектов SubscriptionResponse, содержащего информацию о подписках пользователя
     */
    @Transactional
    @Override
    public Set<SubscriptionResponse> getSubscriptionsByUserId(Long id) {
        return subscriptionRepository.findSubscriptionsByUserId(id);
    }
    /**
     * Возвращает список трех самых популярных подписок.
     *
     * @return список объектов SubscriptionResponse, содержащий три самые популярные подписки
     */
    @Transactional
    @Override
    public List<SubscriptionResponse> getSubscriptionsTop3() {
        return subscriptionRepository.findSubscriptionsTop3().stream().map(subscription ->
                new SubscriptionResponse(subscription.id(), subscription.name())).toList();
    }
}

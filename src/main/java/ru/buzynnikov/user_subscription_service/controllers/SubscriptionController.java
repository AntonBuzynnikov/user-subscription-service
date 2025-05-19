package ru.buzynnikov.user_subscription_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse;
import ru.buzynnikov.user_subscription_service.services.interfaces.SubscriptionService;

import java.util.List;

/**
 * Контроллер для управления доступом к ресурсам подписки через REST API.
 */
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Метод GET-контроллера, возвращающий список трёх наиболее популярных подписок.
     *
     * @return успешный HTTP-ответ с телом в виде списка объектов SubscriptionResponse
     */
    @GetMapping("/top")
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsTop3());
    }
}

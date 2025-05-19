package ru.buzynnikov.user_subscription_service.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionTop3DTO;
import ru.buzynnikov.user_subscription_service.models.Subscription;

import java.util.List;
import java.util.Set;

/**
 * Репозиторий для взаимодействия с базой данных по работе с подписками (Subscription).
 */
@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    /**
     * Поиск всех подписок, принадлежащих указанному пользователю.
     *
     * @param userId идентификатор пользователя
     * @return множество объектов SubscriptionResponse, содержащих информацию о подписках пользователя
     */
    @Transactional
    @Query("""
        SELECT new ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse(s.id, s.name)
        FROM Subscription s
        JOIN s.users u
        WHERE u.id = :userId
        """)
    Set<SubscriptionResponse> findSubscriptionsByUserId(@Param("userId") Long userId);

    /**
     * Получение трех самых популярных подписок (топ-3) на основании количества подписчиков.
     *
     * @return список объектов SubscriptionTop3DTO, содержащий три самые популярные подписки
     */
    @Transactional
    @Query("""
           SELECT new ru.buzynnikov.user_subscription_service.dto.SubscriptionTop3DTO(s.id, s.name, COUNT(u.id) c)
           FROM Subscription s
           JOIN s.users u
           GROUP BY s.id, s.name
           ORDER BY c DESC
           LIMIT 3
           """)
    List<SubscriptionTop3DTO> findSubscriptionsTop3();
}

package ru.buzynnikov.user_subscription_service.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.user_subscription_service.models.User;

/**
 * Репозиторий для взаимодействия с моделью пользователя (User) и связанной связью Many-to-Many (user_subscriptions).
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Добавляет новую связь между пользователем и подпиской путем добавления записи в таблицу user_subscriptions.
     *
     * @param userId        идентификатор пользователя
     * @param subscriptionId идентификатор подписки
     */
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO user_subscriptions (user_id, subscription_id) VALUES (:userId, :subscriptionId)",
            nativeQuery = true
    )
    void addSubscription(@Param("userId") Long userId,
                         @Param("subscriptionId") Long subscriptionId);

    /**
     * Удаляет связь между пользователем и подпиской, соответствующую переданным параметрам, из таблицы user_subscriptions.
     *
     * @param userId        идентификатор пользователя
     * @param subscriptionId идентификатор подписки
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_subscriptions WHERE user_id = :userId AND subscription_id = :subscriptionId", nativeQuery = true)
    void removeSubscriptionFromUser(@Param("userId") Long userId, @Param("subscriptionId") Long subscriptionId);
}

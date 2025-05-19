package ru.buzynnikov.user_subscription_service.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionRequest;
import ru.buzynnikov.user_subscription_service.dto.SubscriptionResponse;
import ru.buzynnikov.user_subscription_service.dto.UserRequest;
import ru.buzynnikov.user_subscription_service.dto.UserResponse;
import ru.buzynnikov.user_subscription_service.exceptions.UserNotFoundException;
import ru.buzynnikov.user_subscription_service.models.User;
import ru.buzynnikov.user_subscription_service.repositories.UserRepository;
import ru.buzynnikov.user_subscription_service.services.interfaces.SubscriptionService;
import ru.buzynnikov.user_subscription_service.services.interfaces.UserService;

import java.util.Set;

/**
 * Реализация стандартного сервиса для работы с пользователями.
 */
@Service
public class UserServiceDefault implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;

    public UserServiceDefault(UserRepository userRepository, SubscriptionService subscriptionService) {
        this.userRepository = userRepository;
        this.subscriptionService = subscriptionService;
    }

    /**
     * Сохраняет нового пользователя на основе переданной информации.
     *
     * @param request объект UserRequest, содержащий информацию о пользователе
     * @return объект UserResponse, представляющий созданного пользователя
     */
    @Transactional
    @Override
    public UserResponse saveUser(UserRequest request) {
        return createUserResponse(userRepository.save(createUser(request)));
    }

    /**
     * Возвращает информацию о пользователе по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект UserResponse, содержащий информацию о пользователе
     */
    @Override
    public UserResponse getUserById(Long id) {
        return createUserResponse(getById(id));
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param id      идентификатор пользователя
     * @param request объект UserRequest, содержащий обновленную информацию
     */
    @Transactional
    @Override
    public void updateUser(Long id, UserRequest request) {
        User user = getById(id);
        user.setUsername(request.name());
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     */
    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Добавляет новую подписку пользователю.
     *
     * @param userId     идентификатор пользователя
     * @param request    объект SubscriptionRequest, содержащий информацию о подписке
     */
    @Transactional
    @Override
    public void addSubscription(Long userId, SubscriptionRequest request) {
        subscriptionService.existsSubscription(request.id());
        existsUser(userId);
        userRepository.addSubscription(userId, request.id());
    }
    /**
     * Возвращает список подписок пользователя.
     *
     * @param userId идентификатор пользователя
     * @return множество объектов SubscriptionResponse, содержащие информацию о подписках пользователя
     */
    @Transactional
    @Override
    public Set<SubscriptionResponse> getUserSubscriptions(Long userId) {
        existsUser(userId);
        return subscriptionService.getSubscriptionsByUserId(userId);
    }
    /**
     * Удаляет подписку у пользователя.
     *
     * @param userId          идентификатор пользователя
     * @param subscriptionId  идентификатор подписки
     */
    @Transactional
    @Override
    public void deleteSubscriptionFormUser(Long userId, Long subscriptionId) {
        userRepository.removeSubscriptionFromUser(userId, subscriptionId);
    }
    /**
     * Создает экземпляр пользователя на основе переданного запроса.
     *
     * @param request объект UserRequest, содержащий информацию о пользователе
     * @return объект User, созданный на основе запроса
     */
    private User createUser(UserRequest request) {
        return new User(request.name());
    }

    /**
     * Преобразует объект User в объект UserResponse.
     *
     * @param user объект User, подлежащий преобразованию
     * @return объект UserResponse, содержащий необходимую информацию о пользователе
     */
    private UserResponse createUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername());
    }

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект User, соответствующий данному идентификатору
     * @throws UserNotFoundException если пользователь не найден
     */
    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    /**
     * Проверяет существование пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @throws UserNotFoundException если пользователь не найден
     */
    private void existsUser(Long id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException("Пользователь не найден");
    }

}

package ru.buzynnikov.user_subscription_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.buzynnikov.user_subscription_service.dto.UserResponse;
import ru.buzynnikov.user_subscription_service.exceptions.SubscriptionNotFoundException;
import ru.buzynnikov.user_subscription_service.exceptions.UserNotFoundException;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(ErrorLog)")
    public Object exceptionLogger(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        if(args[0] instanceof DataIntegrityViolationException exception){
            logger.error("Дублирование данных: {}", exception.getMostSpecificCause().getMessage());
            StackTraceElement[] stackTrace = exception.getMostSpecificCause().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                logger.error(element.toString());
            }
        }

        if(args[0] instanceof UserNotFoundException exception){
            logger.error("Пользователь не найден: {}", exception.getMessage());
        }
        if(args[0] instanceof SubscriptionNotFoundException exception){
            logger.error("Подписка не найдена: {}", exception.getMessage());
        }
        if(args[0] instanceof MethodArgumentNotValidException exception){
            logger.error("Ошибка валидации: {}", exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }

        if(args[0] instanceof HttpMessageNotReadableException exception){
            logger.error("Ошибка десериализации: {}", exception.getMessage());
        }


        return joinPoint.proceed();
    }

    @Around("@annotation(UpdateLog)")
    public Object updateLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        if(methodName.equals("updateUser")){
            logger.info("Пользователь c id {} обновлен, новое имя: {}", args[0], args[1]);
        }

        if(methodName.equals("deleteUser")){
            logger.info("Пользователь c id {} удален", args[0]);
        }

        if(methodName.equals("addSubscription")){
            logger.info("Подписка c id {} добавлена пользователю с id {}", args[1], args[0]);
        }

        if(methodName.equals("deleteSubscriptionFromUser")){
            logger.info("Подписка c id {} удалена у пользователя с id {}", args[1], args[0]);
        }
        return joinPoint.proceed();
    }

    @Around("@annotation(CreateLog)")
    public Object createLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if(result instanceof UserResponse response) {
            logger.info("Пользователь c id {} создан, имя: {}", response.id(), response.name());
        }
        return result;
    }
}

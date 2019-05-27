package com.reckyphiter.crudproxy.shared.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reckyphiter.crudcommon.constant.CommonConstant;
import com.reckyphiter.crudcommon.util.Check;
import com.reckyphiter.crudcommon.util.Try;
import com.reckyphiter.crudproxy.employee.constant.EmployeeConstant;
import com.reckyphiter.crudproxy.shared.constant.Constant;
import com.reckyphiter.crudproxy.shared.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 *
 * @author Recky Phiter
 */
public class AbstractCrudService<T, ID> extends AbstractQueueService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCrudService.class.getName());

    protected final JpaRepository jpaRepository;
    protected final CrudRepository redisRepository;

    public AbstractCrudService(
            @Nonnull RabbitTemplate rabbitTemplate,
            @Nonnull ObjectMapper mapper,
            @Nonnull JpaRepository jpaRepository,
            @Nonnull CrudRepository redisRepository) {

        super(rabbitTemplate, mapper);

        Check.nonNull(jpaRepository, "jpaRepository required !");
        Check.nonNull(redisRepository, "redisRepository required !");

        this.jpaRepository = jpaRepository;
        this.redisRepository = redisRepository;
    }

    protected Response add(@Nonnull T object, @Nonnull ID objectId, String entityType, String entityName) {
        Check.hasText(entityType,  "Entity type required !");
        Check.hasText(entityName,  "Entity name required !");
        Check.nonNull(object, entityType + " required !");
        Check.nonNull(objectId, entityType + " Id required !");

        final Optional<T> optRedisObject = Try.of(() -> redisRepository.findById(objectId),
                "Failed to get " + entityName + " data from cache");

        if (optRedisObject.isPresent()) {
            final String message = "Cannot " + CommonConstant.ADD + " " + entityName + " because data already exist";
            LOG.error("{} {}: {}", Constant.APP_ERROR_TAG, message, optRedisObject.get());
            return new Response(HttpStatus.BAD_REQUEST.value(), message);
        }

        return sendMessage(object, entityType, entityName, CommonConstant.ADD, EmployeeConstant.EMPLOYEE_ROUTING_KEY_ADD);
    }

    protected Response update(@Nonnull T object, @Nonnull ID objectId, String entityType, String entityName) {
        Check.hasText(entityType,  "Entity type required !");
        Check.hasText(entityName,  "Entity name required !");
        Check.nonNull(object, entityType + " required !");
        Check.nonNull(objectId, entityType + " Id required !");

        final Optional<T> optRedisObject = Try.of(() -> redisRepository.findById(objectId),
                "Failed to get " + entityName + " data from cache");

        if (!optRedisObject.isPresent()) {
            final String message = "Cannot " + CommonConstant.UPDATE + " " + entityName + " because data not found";
            LOG.error("{} {}", Constant.APP_ERROR_TAG, message);
            return new Response(HttpStatus.BAD_REQUEST.value(), message);
        }

        return sendMessage(object, entityType, entityName, CommonConstant.UPDATE, EmployeeConstant.EMPLOYEE_ROUTING_KEY_UPDATE);
    }

    protected Response delete(@Nonnull ID objectId, String entityType, String entityName) {
        Check.hasText(entityType,  "Entity type required !");
        Check.hasText(entityName,  "Entity name required !");
        Check.nonNull(objectId, entityType + " required !");

        final Optional<T> optRedisObject = Try.of(() -> redisRepository.findById(objectId),
                "Failed to get " + entityName + " data from cache");

        if (!optRedisObject.isPresent()) {
            final String message = "Cannot " + CommonConstant.DELETE + " " + entityName + " because data not found";
            LOG.error("{} {}", Constant.APP_ERROR_TAG, message);
            return new Response(HttpStatus.BAD_REQUEST.value(), message);
        }

        return sendMessage(objectId, entityType, entityName, CommonConstant.DELETE, EmployeeConstant.EMPLOYEE_ROUTING_KEY_DELETE);
    }

    protected T get(@Nonnull ID objectId, String entityType, String entityName) {
        Check.hasText(entityType,  "Entity type required !");
        Check.hasText(entityName,  "Entity name required !");
        Check.nonNull(objectId, entityType + " required !");

        final Optional<T> optRedisObject = Try.of(() -> redisRepository.findById(objectId),
                "Failed to get " + entityName + " data from cache");

        if (optRedisObject.isPresent()) {
            return optRedisObject.get();
        }

        final Optional<T> optDbObject = Try.of(() -> jpaRepository.findById(objectId),
                "Failed to get " + entityName + " data from db");

        final T dbObject = Try.of(() -> optDbObject.get(), entityName + " data not found");
        Try.ofVoidSafe(() -> redisRepository.save(dbObject));
        return dbObject;
    }
}

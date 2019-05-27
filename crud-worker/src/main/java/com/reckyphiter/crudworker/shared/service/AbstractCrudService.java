package com.reckyphiter.crudworker.shared.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.reckyphiter.crudcommon.constant.CommonConstant;
import com.reckyphiter.crudcommon.util.Check;
import com.reckyphiter.crudcommon.util.Try;
import com.reckyphiter.crudworker.shared.constant.Constant;
import com.reckyphiter.crudworker.shared.model.QueueMessage;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 *
 * @author Recky Phiter
 */
public abstract class AbstractCrudService<T, ID> extends AbstractQueueService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractCrudService.class.getName());

    private final MapperFacade mapperFacade;
    private final ObjectMapper mapper;

    protected final JpaRepository jpaRepository;
    protected final CrudRepository redisRepository;
    protected final Class<T> entityClass;
    protected final Class<ID> identityClass;

    public AbstractCrudService(
            @Nonnull RabbitTemplate rabbitTemplate,
            @Nonnull AmqpAdmin amqpAdmin,
            @Nonnull ObjectMapper mapper,
            @Nonnull JpaRepository jpaRepository,
            @Nonnull CrudRepository redisRepository,
            @Nonnull Class<T> entityClass,
            @Nonnull Class<ID> identityClass) {

        super(rabbitTemplate, amqpAdmin, mapper);

        Check.nonNull(jpaRepository, "jpaRepository required !");
        Check.nonNull(redisRepository, "redisRepository required !");
        Check.nonNull(entityClass, "entityClass required !");
        Check.nonNull(identityClass, "identityClass required !");

        this.jpaRepository = jpaRepository;
        this.redisRepository = redisRepository;
        this.entityClass = entityClass;
        this.identityClass = identityClass;

        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
        mapperFactory.classMap(entityClass, entityClass);

        this.mapperFacade = mapperFactory.getMapperFacade();
        this.mapper = mapper;
        this.mapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
    }

    protected Optional<T> findEntity(String entityName, String queueName) {
        Check.hasText(entityName, "Entity name required !");
        Check.hasText(queueName, "Queue name required !");

        final Optional<QueueMessage<T>> optQueueMessage = getQueueMessage(queueName);

        if (!optQueueMessage.isPresent()) {
            return Optional.empty();
        }

        final T object = mapper.convertValue(optQueueMessage.get().getMessage(), entityClass);
        return Optional.ofNullable(object);
    }

    protected void add(T object, ID objectId, String entityName) {
        final Optional<T> optCurrentObject = Try.of(() -> jpaRepository.findById(objectId),
                "Failed to get " + entityName + " data from db");

        if (optCurrentObject.isPresent()) {
            LOG.error("{} Cannot {} {} because data already exist: {}", Constant.APP_ERROR_TAG, CommonConstant.ADD, entityName, optCurrentObject.get());
            return;
        }

        save(object, entityName, CommonConstant.ADD);
    }

    protected void update(T object, ID objectId, String entityName) {
        final Optional<T> optCurrentObject = Try.of(() -> jpaRepository.findById(objectId),
                "Failed to get " + entityName + " data from db");

        if (!optCurrentObject.isPresent()) {
            LOG.error("{} Cannot {} {} because data not found: {}", Constant.APP_ERROR_TAG, CommonConstant.UPDATE, entityName, optCurrentObject.get());
            return;
        }

        final T mappingObject = optCurrentObject.get();
        mapperFacade.map(object, mappingObject);
        save(mappingObject, entityName, CommonConstant.UPDATE);
    }

    protected void delete(String entityName, String queueName) {
        Check.hasText(entityName, "Entity name required !");
        Check.hasText(queueName, "Queue name required !");

        final Optional<QueueMessage<ID>> optQueueMessage = getQueueMessage(queueName);

        if (!optQueueMessage.isPresent()) {
            return;
        }

        final ID objectId = mapper.convertValue(optQueueMessage.get().getMessage(), identityClass);
        final Optional<ID> optObject = Try.of(() -> jpaRepository.findById(objectId),
                "Failed to get " + entityName + " data from db");

        if (!optObject.isPresent()) {
            LOG.error("{} Cannot {} {} because data not found", Constant.APP_ERROR_TAG, CommonConstant.DELETE, entityName);
            return;
        }

        LOG.info("Start {} {} with id: {}", CommonConstant.DELETE, entityName, objectId);

        final ID object = optObject.get();
        Try.ofVoid(() -> jpaRepository.delete(object), "Failed to " + CommonConstant.DELETE + " " + entityName + " !");
        Try.ofVoidSafe(() -> redisRepository.delete(object));

        LOG.info("Successfully {} {}", CommonConstant.DELETE, entityName);
    }

    private void save(@Nonnull T object, String entityName, String action) {
        Check.hasText(entityName, "Entity name required !");
        Check.hasText(action, "Action required !");
        Check.nonNull(object, entityName + " required !");

        LOG.info("Start {} {} with data: {}", action, entityName, object);

        final T savedObject = Try.of(() -> (T) jpaRepository.save(object), "Failed to " + action + " " + entityName + " !");
        Try.ofVoidSafe(() -> redisRepository.save(savedObject));

        LOG.info("Successfully {} {}", action, entityName);
    }
}

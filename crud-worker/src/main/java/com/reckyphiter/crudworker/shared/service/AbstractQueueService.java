package com.reckyphiter.crudworker.shared.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.reckyphiter.crudcommon.util.Check;
import com.reckyphiter.crudcommon.util.Try;
import com.reckyphiter.crudworker.shared.constant.Constant;
import com.reckyphiter.crudworker.shared.model.QueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 *
 * @author Recky Phiter
 */
public abstract class AbstractQueueService {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractQueueService.class.getName());

    protected final RabbitTemplate rabbitTemplate;

    protected final AmqpAdmin amqpAdmin;

    protected final ObjectMapper mapper;

    public AbstractQueueService(
            @Nonnull RabbitTemplate rabbitTemplate,
            @Nonnull AmqpAdmin amqpAdmin,
            @Nonnull ObjectMapper mapper) {

        Check.nonNull(rabbitTemplate, "rabbitTemplate required !");
        Check.nonNull(amqpAdmin, "amqpAdmin required !");
        Check.nonNull(mapper, "mapper required !");

        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
        this.mapper = mapper;
        this.mapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
    }

    protected <T> Optional<QueueMessage<T>> getQueueMessage(String queueName) {
        Check.hasText(queueName, "Queue name required !");

        final int messageCount = Try.ofDefault(() -> (int) amqpAdmin.getQueueProperties(queueName).get(Constant.QUEUE_MESSAGE_COUNT), 0);

        if (messageCount < 1) {
            return Optional.empty();
        }

        LOG.info("Start consume message from queue {}", queueName);

        final Message message = rabbitTemplate.receive(queueName);
        final String textMessage = message.toString();
        final byte[] body = Try.of(() -> message.getBody(),
                "Failed to consume " + queueName + " !");
        final QueueMessage<T> queueMessage = Try.of(() ->  mapper.readValue(body, QueueMessage.class),
                "Invalid queue message model !");

        LOG.info("Successfully consume message from queue {} with content: {}", queueName, queueMessage);

        return Optional.of(queueMessage);
    }
}

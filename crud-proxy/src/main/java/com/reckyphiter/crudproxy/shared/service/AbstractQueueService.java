package com.reckyphiter.crudproxy.shared.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.reckyphiter.crudcommon.util.Check;
import com.reckyphiter.crudcommon.util.Generator;
import com.reckyphiter.crudproxy.employee.constant.EmployeeConstant;
import com.reckyphiter.crudproxy.shared.model.QueueMessage;
import com.reckyphiter.crudproxy.shared.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;

/**
 *
 * @author Recky Phiter
 */
public abstract class AbstractQueueService {

    @Value("${rabbitmq.x-delay}")
    private int xDelay;

    private static final Logger LOG = LoggerFactory.getLogger(AbstractQueueService.class.getName());

    protected final RabbitTemplate rabbitTemplate;

    protected final ObjectMapper mapper;

    public AbstractQueueService(
            @Nonnull RabbitTemplate rabbitTemplate,
            @Nonnull ObjectMapper mapper) {

        Check.nonNull(rabbitTemplate, "rabbitTemplate required !");
        Check.nonNull(mapper, "mapper required !");

        this.rabbitTemplate = rabbitTemplate;
        this.mapper = mapper;
        this.mapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
    }

    protected <T> Response sendMessage(
            @Nonnull T entity,
            String entityType,
            String entityName,
            String action,
            String routingKey) {

        Check.hasText(entityType,  "Entity type required !");
        Check.hasText(entityName,  "Entity name required !");
        Check.hasText(action, "Action required !");
        Check.hasText(routingKey, "Routing key required !");
        Check.nonNull(entity, entityType + " required !");

        final String messageId = Generator.generateMessageId();
        final QueueMessage queueMessage = new QueueMessage(messageId, entity);

        try {
            LOG.info("Start send {} message for {} operation to queue with routingKey: {}, message: {}",
                    entityName, action, messageId, routingKey, queueMessage);

            rabbitTemplate.convertAndSend(EmployeeConstant.EMPLOYEE_EXCHANGE, routingKey, queueMessage, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setHeader("x-delay", xDelay);
                    return message;
                }
            });

            LOG.info("Successfully send {} message for {} operation to queue with routingKey: {}, messageId: {}", routingKey, entityName, action, messageId);
            return new Response(HttpStatus.OK.value(), "Success sending to queue");

        } catch (Exception ex) {
            LOG.error("Error while sending {} message for {} operation to queue with routingKey: {}, messageId: {}", routingKey, entityName, action, messageId, ex);
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed sending to queue caused by system error");
        }
    }
}

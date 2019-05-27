package com.reckyphiter.crudworker.employee.config;

import com.reckyphiter.crudworker.employee.constant.EmployeeConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Recky Phiter
 */
@Configuration
public class EmployeeQueueConfig {

    @Bean
    public Queue queueAdd() {
        return new Queue(EmployeeConstant.EMPLOYEE_QUEUE_ADD);
    }

    @Bean
    public Queue queueUpdate() {
        return new Queue(EmployeeConstant.EMPLOYEE_QUEUE_UPDATE);
    }

    @Bean
    public Queue queueDelete() {
        return new Queue(EmployeeConstant.EMPLOYEE_QUEUE_DELETE);
    }

    @Bean
    public CustomExchange exchange() {
        final Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "topic");
        return new CustomExchange(EmployeeConstant.EMPLOYEE_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingAdd() {
        return BindingBuilder.bind(queueAdd()).to(exchange()).with(EmployeeConstant.EMPLOYEE_ROUTING_KEY_ADD).noargs();
    }

    @Bean
    public Binding bindingUpdate() {
        return BindingBuilder.bind(queueUpdate()).to(exchange()).with(EmployeeConstant.EMPLOYEE_ROUTING_KEY_UPDATE).noargs();
    }

    @Bean
    public Binding bindingDelete() {
        return BindingBuilder.bind(queueDelete()).to(exchange()).with(EmployeeConstant.EMPLOYEE_ROUTING_KEY_DELETE).noargs();
    }
}

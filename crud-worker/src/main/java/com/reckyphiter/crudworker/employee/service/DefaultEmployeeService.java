package com.reckyphiter.crudworker.employee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reckyphiter.crudworker.employee.constant.EmployeeConstant;
import com.reckyphiter.crudworker.employee.model.Employee;
import com.reckyphiter.crudworker.employee.model.EmployeeId;
import com.reckyphiter.crudworker.employee.repository.EmployeeJpaRepository;
import com.reckyphiter.crudworker.employee.repository.EmployeeRedisRepository;
import com.reckyphiter.crudworker.shared.service.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author Recky Phiter
 */
@Named("DefaultEmployeeService")
public class DefaultEmployeeService extends AbstractCrudService<Employee, EmployeeId> implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultEmployeeService.class.getName());

    @Inject
    public DefaultEmployeeService(
            @Nonnull RabbitTemplate rabbitTemplate,
            @Nonnull AmqpAdmin amqpAdmin,
            @Nonnull ObjectMapper mapper,
            @Nonnull EmployeeJpaRepository employeeJpaRepository,
            @Nonnull EmployeeRedisRepository employeeRedisRepository) {

        super(rabbitTemplate, amqpAdmin, mapper, employeeJpaRepository, employeeRedisRepository, Employee.class, EmployeeId.class);
    }

    @Override
    public void add() {
        final Optional<Employee> optEmployee = findEntity(EmployeeConstant.EMPLOYEE, EmployeeConstant.EMPLOYEE_QUEUE_ADD);

        if (!optEmployee.isPresent()) {
            return;
        }

        final Employee employee = optEmployee.get();
        employee.setCreatedAt(new Date());
        add(employee, employee.getEmployeeId(), EmployeeConstant.EMPLOYEE);
    }

    @Override
    public void update() {
        final Optional<Employee> optEmployee = findEntity(EmployeeConstant.EMPLOYEE, EmployeeConstant.EMPLOYEE_QUEUE_UPDATE);

        if (!optEmployee.isPresent()) {
            return;
        }

        final Employee employee = optEmployee.get();
        employee.setUpdatedAt(new Date());
        update(employee, employee.getEmployeeId(), EmployeeConstant.EMPLOYEE);
    }

    @Override
    public void delete() {
        delete(EmployeeConstant.EMPLOYEE, EmployeeConstant.EMPLOYEE_QUEUE_DELETE);
    }
}

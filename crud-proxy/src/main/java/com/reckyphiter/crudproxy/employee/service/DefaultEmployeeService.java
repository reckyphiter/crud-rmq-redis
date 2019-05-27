package com.reckyphiter.crudproxy.employee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reckyphiter.crudproxy.employee.model.Employee;
import com.reckyphiter.crudproxy.employee.model.EmployeeId;
import com.reckyphiter.crudproxy.employee.repository.EmployeeJpaRepository;
import com.reckyphiter.crudproxy.employee.repository.EmployeeRedisRepository;
import com.reckyphiter.crudproxy.shared.model.Response;
import com.reckyphiter.crudproxy.shared.service.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Recky Phiter
 */
@Named("DefaultEmployeeService")
public class DefaultEmployeeService extends AbstractCrudService<Employee, EmployeeId> implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmployeeService.class.getName());

    @Inject
    public DefaultEmployeeService(
            @Nonnull RabbitTemplate rabbitTemplate,
            @Nonnull ObjectMapper mapper,
            @Nonnull EmployeeJpaRepository employeeJpaRepository,
            @Nonnull EmployeeRedisRepository employeeRedisRepository) {

        super(rabbitTemplate, mapper, employeeJpaRepository, employeeRedisRepository);
    }

    @Override
    public Response add(@Nonnull Employee employee) {
        return add(employee, employee.getEmployeeId(), "Employee", "Employee");
    }

    @Override
    public Response update(@Nonnull Employee employee) {
        return update(employee, employee.getEmployeeId(), "Employee", "Employee");
    }

    @Override
    public Response delete(@Nonnull EmployeeId employeeId) {
        return delete(employeeId, "Employee Id", "Employee");
    }

    @Override
    public Employee get(@Nonnull EmployeeId employeeId) {
        return get(employeeId, "Employee Id", "Employee");
    }
}

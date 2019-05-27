package com.reckyphiter.crudproxy.employee.repository;

import com.reckyphiter.crudproxy.employee.model.Employee;
import com.reckyphiter.crudproxy.employee.model.EmployeeId;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Recky Phiter
 */
public interface EmployeeRedisRepository extends CrudRepository<Employee, EmployeeId> {
}

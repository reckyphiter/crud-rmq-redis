package com.reckyphiter.crudworker.employee.repository;

import com.reckyphiter.crudworker.employee.model.Employee;
import com.reckyphiter.crudworker.employee.model.EmployeeId;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Recky Phiter
 */
public interface EmployeeRedisRepository extends CrudRepository<Employee, EmployeeId> {
}

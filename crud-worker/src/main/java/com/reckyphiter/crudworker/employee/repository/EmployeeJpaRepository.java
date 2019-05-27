package com.reckyphiter.crudworker.employee.repository;

import com.reckyphiter.crudworker.employee.model.Employee;
import com.reckyphiter.crudworker.employee.model.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Recky Phiter
 */
public interface EmployeeJpaRepository extends JpaRepository<Employee, EmployeeId> {
}

package com.reckyphiter.crudproxy.employee.repository;

import com.reckyphiter.crudproxy.employee.model.Employee;
import com.reckyphiter.crudproxy.employee.model.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Recky Phiter
 */
public interface EmployeeJpaRepository extends JpaRepository<Employee, EmployeeId> {
}

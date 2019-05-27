package com.reckyphiter.crudproxy.employee.service;

import com.reckyphiter.crudproxy.employee.model.Employee;
import com.reckyphiter.crudproxy.employee.model.EmployeeId;
import com.reckyphiter.crudproxy.shared.model.Response;
import com.reckyphiter.crudproxy.shared.service.CrudService;

/**
 *
 * @author Recky Phiter
 */
public interface EmployeeService extends CrudService<Employee, EmployeeId, Response> {
}

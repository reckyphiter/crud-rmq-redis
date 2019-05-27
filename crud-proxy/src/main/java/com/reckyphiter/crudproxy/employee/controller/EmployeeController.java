package com.reckyphiter.crudproxy.employee.controller;

import com.reckyphiter.crudcommon.constant.CommonConstant;
import com.reckyphiter.crudcommon.util.Check;
import com.reckyphiter.crudproxy.employee.model.Employee;
import com.reckyphiter.crudproxy.employee.model.EmployeeId;
import com.reckyphiter.crudproxy.employee.service.EmployeeService;
import com.reckyphiter.crudproxy.shared.model.Response;
import com.reckyphiter.crudproxy.shared.util.ResponseUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author Recky Phiter
 */
@RestController
@RequestMapping("employee")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    private final Validator validator;
    
    @Inject
    public EmployeeController(@Nonnull @Named("DefaultEmployeeService") EmployeeService employeeService) {
        Check.nonNull(employeeService, "Employee service required !");
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.employeeService = employeeService;
        this.validator = validatorFactory.getValidator();
    }

    @PostMapping(value = CommonConstant.ADD, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody Employee employee) {
        Check.validateModel(validator, employee);
        final Response response = employeeService.add(employee);
        return ResponseUtil.composeResponse(response);
    }

    @PutMapping(value = CommonConstant.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Employee employee) {
        Check.validateModel(validator, employee.getEmployeeId());
        final Response response = employeeService.update(employee);
        return ResponseUtil.composeResponse(response);
    }

    @DeleteMapping(value = CommonConstant.DELETE + "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable("employeeId") EmployeeId employeeId) {
        Check.validateModel(validator, employeeId);
        final Response response = employeeService.delete(employeeId);
        return ResponseUtil.composeResponse(response);
    }

    @GetMapping(value = CommonConstant.GET + "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable("employeeId") EmployeeId employeeId) {
        Check.validateModel(validator, employeeId);
        final Employee employee = employeeService.get(employeeId);
        return ResponseEntity.ok(employee);
    }
}

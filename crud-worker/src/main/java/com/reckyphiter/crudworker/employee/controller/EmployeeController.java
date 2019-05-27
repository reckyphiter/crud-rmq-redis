package com.reckyphiter.crudworker.employee.controller;

import com.reckyphiter.crudcommon.util.Check;
import com.reckyphiter.crudworker.employee.service.EmployeeService;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Recky Phiter
 */
@Named("EmployeeController")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Inject
    public EmployeeController(@Nonnull @Named("DefaultEmployeeService") EmployeeService employeeService) {
        Check.nonNull(employeeService, "Employee service required !");
        this.employeeService = employeeService;
    }

    @Scheduled(fixedRateString = "${schedule.interval:100}")
    public void add() {
        employeeService.add();
    }

    @Scheduled(fixedRateString = "${schedule.interval:100}")
    public void update() {
        employeeService.update();
    }

    @Scheduled(fixedRateString = "${schedule.interval:100}")
    public void delete() {
        employeeService.delete();
    }
}

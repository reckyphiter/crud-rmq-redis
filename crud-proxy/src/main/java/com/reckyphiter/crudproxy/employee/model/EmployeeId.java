package com.reckyphiter.crudproxy.employee.model;

import com.reckyphiter.crudproxy.shared.model.AbstractEntity;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 *
 * @author Recky Phiter
 */
@Data
@Embeddable
public class EmployeeId extends AbstractEntity {

    @Size(min = 5, max = 12, message = "Id must be between 5 and 12 characters")
    private String id;
}

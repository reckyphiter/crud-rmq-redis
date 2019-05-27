package com.reckyphiter.crudworker.employee.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author Recky Phiter
 */
@Data
@Embeddable
public class EmployeeId implements Serializable {

    @Column(name = "ID", length = 12)
    private String id;
}

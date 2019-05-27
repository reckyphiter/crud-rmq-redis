package com.reckyphiter.crudworker.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Recky Phiter
 */
@Data
@Entity
@Table(name = "TBL_EMPLOYEE")
public class Employee implements Serializable {

    @EmbeddedId
    private EmployeeId employeeId;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    @Column(name = "PHONE", length = 14, nullable = false)
    private String phone;

    @Column(name = "ADDRESS", length = 100, nullable = false)
    private String address;

    @Column(name = "GENDER", length = 6, nullable = false)
    private String gender;

    @Column(name = "RELIGION", length = 8, nullable = false)
    private String religion;

    @Column(name = "JOB_TITLE", length = 50, nullable = false)
    private String jobTitle;

    @Column(name = "DIVISION", length = 30, nullable = false)
    private String division;

    @Column(name = "DEPARTMENT", length = 30, nullable = false)
    private String department;

    @Column(name = "GRADE", length = 2, nullable = false)
    private String grade;

    @Column(name = "SALARY", nullable = false)
    private int salary;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "BIRTH_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "JOIN_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date joinDate;

    @Column(name = "RESIGN_DATE")
    @Temporal(TemporalType.DATE)
    private Date resignDate;

    @JsonIgnore
    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonIgnore
    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}

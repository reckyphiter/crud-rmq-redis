package com.reckyphiter.crudproxy.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reckyphiter.crudcommon.util.Enum;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author Recky Phiter
 */
@Data
@Entity
@Table(name = "TBL_EMPLOYEE")
public class Employee implements Serializable {

    @Valid
    @EmbeddedId
    private EmployeeId employeeId;
    
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Email(message = "Invalid email")
    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    // 1) Start with +62 or 62 or 0
    // 2) Then contains number 8
    // 3) Then contains 8-10 digits
    @Pattern(regexp = "(\\+62|62|0)[8][0-9]{8,10}", message = "Invalid phone number")
    @Column(name = "PHONE", length = 14, nullable = false)
    private String phone;

    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
    @Column(name = "ADDRESS", length = 100, nullable = false)
    private String address;

    @Enum(enumClass = Gender.class, ignoreCase = true, message = "Gender must be one of [Male, Female]")
    @Column(name = "GENDER", length = 6, nullable = false)
    private String gender;

    @Enum(enumClass = Religion.class, ignoreCase = true, message = "Religion must be one of [Islam, Kristen, Katolik, Hindu, Buddha, Konghucu, Other]")
    @Column(name = "RELIGION", length = 8, nullable = false)
    private String religion;

    @Size(min = 1, max = 50, message = "Job Title must be between 1 and 50 characters")
    @Column(name = "JOB_TITLE", length = 50, nullable = false)
    private String jobTitle;

    @Size(min = 1, max = 30, message = "Division must be between 1 and 30 characters")
    @Column(name = "DIVISION", length = 30, nullable = false)
    private String division;

    @Size(min = 1, max = 30, message = "Department must be between 1 and 30 characters")
    @Column(name = "DEPARTMENT", length = 30, nullable = false)
    private String department;

    @Size(min = 1, max = 2, message = "Division must be between 1 and 2 characters")
    @Column(name = "GRADE", length = 2, nullable = false)
    private String grade;

    @Positive(message = "Salary must be greater than zero")
    @Column(name = "SALARY", nullable = false)
    private int salary;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @Past(message = "Birth date must be past")
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

    public static enum Gender {
        MALE("Male"),
        FEMALE("Female");

        public String value;

        private Gender(String value) {
            this.value = value;
        }

        public static Gender value(String name) {
            return Optional.ofNullable(Gender.valueOf(name.toUpperCase())).orElse(Gender.MALE);
        }
    }

    public static enum Religion {
        ISLAM("Islam"),
        KRISTEN("Kristen"),
        KATOLIK("Katolik"),
        HINDU("Hindu"),
        BUDDHA("Buddha"),
        KONGHUCU("Konghucu"),
        OTHER("Other");

        public String value;

        private Religion(String value) {
            this.value = value;
        }

        public static Religion value(String name) {
            return Optional.ofNullable(Religion.valueOf(name.toUpperCase())).orElse(Religion.OTHER);
        }
    }
}

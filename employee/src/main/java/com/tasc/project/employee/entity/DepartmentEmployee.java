package com.tasc.project.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department_employee")
public class DepartmentEmployee {

    @EmbeddedId
    private DePk dePk;

    @Embeddable
    @Getter
    @Setter
    public static class DePk implements Serializable {
        @Column(name = "department_id")
        private long departmentId;

        @Column(name = "employee_id")
        private long employeeId;

        public DePk() {
        }

        public DePk(long departmentId, long employeeId) {
            this.departmentId = departmentId;
            this.employeeId = employeeId;
        }
    }
}

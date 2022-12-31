package com.tasc.project.department.repository;

import com.tasc.project.department.entity.DepartmentEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, DepartmentEmployee.DePk> {
}

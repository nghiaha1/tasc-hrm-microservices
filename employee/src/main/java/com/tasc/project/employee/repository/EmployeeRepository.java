package com.tasc.project.employee.repository;

import com.tasc.entity.BaseStatus;
import com.tasc.project.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findEmployeesByStatus(BaseStatus status, Pageable pageable);

    List<Employee> findEmployeesByFullNameContainingIgnoreCase(String fullName);
}

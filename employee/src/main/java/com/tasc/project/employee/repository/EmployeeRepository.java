package com.tasc.project.employee.repository;

import com.tasc.project.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeesByFullNameContainingIgnoreCase(String fullName);

    @Query(value = "SELECT * FROM employees WHERE status = ?1", nativeQuery = true)
    Page<Employee> findEmployeesByStatus(String status, Pageable pageable);
}

package com.tasc.project.employee.repository;


import com.tasc.entity.BaseStatus;
import com.tasc.project.employee.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Page<Department> findAllByStatus(BaseStatus status, Pageable pageable);

    List<Department> findDepartmentByNameContaining(String name);

    @Query(value = "SELECT * " +
            "FROM departments d " +
            "JOIN department_relationship dr " +
            "ON d.id = dr.parent_id WHERE 1 = 1 " +
            "AND dr.child_id " +
            "IN (SELECT d1.id FROM departments d1 WHERE d1.name LIKE '%' ?1 '%') " +
            "GROUP BY d.id"
            , nativeQuery = true)
    List<Department> findParentsByChild(String name);

    @Query(value = "SELECT * " +
            "FROM departments d " +
            "JOIN department_relationship dr " +
            "ON d.id = dr.child_id WHERE 1 = 1 " +
            "AND dr.parent_id IN (SELECT d1.id FROM departments d1 WHERE d1.name LIKE '%' ?1 '%') " +
            "GROUP BY d.id"
            , nativeQuery = true)
    List<Department> findChildrenByParent(String name);

}

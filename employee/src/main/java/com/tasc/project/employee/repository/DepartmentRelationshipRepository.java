package com.tasc.project.employee.repository;

import com.tasc.project.employee.entity.DepartmentRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRelationshipRepository extends JpaRepository<DepartmentRelationship, DepartmentRelationship.DrPk> {
}

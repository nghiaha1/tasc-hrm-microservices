package com.tasc.project.department.repository;

import com.tasc.project.department.entity.DepartmentRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRelationshipRepository extends JpaRepository<DepartmentRelationship, DepartmentRelationship.DrPk> {
}

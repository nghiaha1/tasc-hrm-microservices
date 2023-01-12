package com.tasc.project.attendance.search;

import com.tasc.project.attendance.entity.Attendance;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class AttendanceSpecification implements org.springframework.data.jpa.domain.Specification<Attendance> {


    private SearchCriteria searchCriteria;

    public AttendanceSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    // criteriaBuilder giúp xử lý các toán tử.
    // root lấy ra trường và giá trị các trường.
    @Override
    public Predicate toPredicate(
            Root<Attendance> root,
            CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperator()) {
            case EQUALS:
                return criteriaBuilder.equal(
                        root.get(searchCriteria.getKey()),
                        searchCriteria.getValue());
            case LIKE:
                return criteriaBuilder.like(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN_OR_EQUALS:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                } else {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(searchCriteria.getKey()),
                            String.valueOf(searchCriteria.getValue()));
                }
            case LESS_THAN:
                return criteriaBuilder.lessThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                } else {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(searchCriteria.getKey()),
                            String.valueOf(searchCriteria.getValue()));
                }
        }
        return null;
    }
}

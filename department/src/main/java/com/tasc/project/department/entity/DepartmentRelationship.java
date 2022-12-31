package com.tasc.project.department.entity;

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
@Table(name = "department_relationship")
public class DepartmentRelationship {
    @EmbeddedId
    private DrPk drPk;

    @Embeddable
    @Getter
    @Setter
    public static class DrPk implements Serializable {
        @Column(name = "parent_id")
        private long parentId;

        @Column(name = "child_id")
        private long childId;

        public DrPk() {
        }

        public DrPk(long parentId, long childId) {
            this.parentId = parentId;
            this.childId = childId;
        }
    }
}

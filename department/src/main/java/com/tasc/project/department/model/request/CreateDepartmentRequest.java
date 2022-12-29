package com.tasc.project.department.model.request;

import com.tasc.entity.BaseStatus;
import com.tasc.entity.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepartmentRequest {
    private String name;

    private String description;

    private String detail;

    private List<Long> employeeIdList;

    private List<Long> childrenDepartmentIdList;

    private Long parentDepartmentId;

    private Integer isRoot;

    private BaseStatus status;

    public boolean checkIsRoot(){
        return isRoot != null && isRoot == Constant.ONOFF.ON;
    }
}

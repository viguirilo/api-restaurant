package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionResponseVO {

    private Long id;
    private String name;
    private String description;

    public PermissionResponseVO(Permission permission) {
        BeanUtils.copyProperties(permission, this);
    }

}

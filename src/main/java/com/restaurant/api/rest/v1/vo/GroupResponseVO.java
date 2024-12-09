package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.Group;
import com.restaurant.api.rest.v1.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupResponseVO {

    private Long id;
    private String name;
    private List<Permission> permissions;

    public GroupResponseVO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.permissions = group.getPermissions();
    }

}

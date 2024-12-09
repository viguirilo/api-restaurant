package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.PermissionRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "permission")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "description", nullable = false)
    @JsonProperty(value = "description")
    private String description;

    public Permission(PermissionRequestVO permissionRequestVO) {
        this.name = permissionRequestVO.getName();
        this.description = permissionRequestVO.getDescription();
    }

}

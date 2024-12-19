package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionRequestVO {

    @NotBlank
    @Size(max = 80)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String description;

}

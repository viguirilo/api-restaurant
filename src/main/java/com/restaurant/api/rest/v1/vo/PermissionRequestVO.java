package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a request value object containing permission information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PermissionRequestVO {

    @Schema(description = "The name of the permission being saved or updated")
    @NotBlank
    @Size(max = 80)
    private String name;

    @Schema(description = "The description of the permission being saved or updated")
    @NotBlank
    @Size(max = 255)
    private String description;

}

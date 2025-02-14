package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a request value object containing kitchen information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KitchenRequestVO {

    @Schema(description = "The name of the kitchen being saved or updated")
    @NotBlank
    @Size(max = 100)
    public String name;

}

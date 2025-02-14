package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a request value object containing city information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityRequestVO {

    @Schema(description = "The name of the city being saved or updated")
    @NotBlank
    @Size(max = 200)
    public String name;

    @Schema(description = "The ID of the state related to city being saved or updated")
    @NotNull
    @DecimalMin("1")
    public Long stateId;

}
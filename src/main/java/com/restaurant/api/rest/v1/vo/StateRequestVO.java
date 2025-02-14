package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a request value object containing state information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StateRequestVO {

    @Schema(description = "The name of the state being saved or updated")
    @NotBlank
    @Size(max = 80)
    public String name;

    @Schema(description = "The abbreviation of the state being saved or updated")
    @NotBlank
    @Size(max = 5)
    public String abbreviation;

    @Schema(description = "The country of the state being saved or updated")
    @NotBlank
    @Size(max = 25)
    public String country;

}

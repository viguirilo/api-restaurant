package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Represents a request value object containing restaurant information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantRequestVO {

    @Schema(description = "The name of the restaurant being saved or updated")
    @NotBlank
    @Size(max = 80)
    public String name;

    @Schema(description = "The ship rate of the restaurant being saved or updated")
    @NotNull
    @PositiveOrZero
    public BigDecimal shipRate;

    @Schema(description = "The flag representing if a restaurant is active (true) or not (false)")
    @NotNull
    public Boolean active;

    @Schema(description = "The flag representing if a restaurant is opened (true) or not (false)")
    @NotNull
    public Boolean open;

    @Schema(description = "The kitchen ID of the restaurant being saved or updated")
    @NotNull
    @DecimalMin("1")
    public Long kitchenId;

    @Schema(description = "The address street or avenue of the restaurant being saved or updated")
    @NotBlank
    @Size(max = 100)
    public String addressStreetOrAvenue;

    @Schema(description = "The address number of the restaurant being saved or updated")
    @NotBlank
    @Size(max = 10)
    public String addressNumber;

    @Schema(description = "The address complement of the restaurant being saved or updated")
    @Size(max = 50)
    public String addressComplement;

    @Schema(description = "The address neighborhood of the restaurant being saved or updated")
    @NotBlank
    @Size(max = 50)
    public String addressNeighborhood;

    @Schema(description = "The address zip code of the restaurant being saved or updated")
    @NotBlank
    @Size(max = 50)
    public String addressZipCode;

    @Schema(description = "The address city ID of the restaurant being saved or updated")
    @NotNull
    @DecimalMin("1")
    public Long addressCityId;

}

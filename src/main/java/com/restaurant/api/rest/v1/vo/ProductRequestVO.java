package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Represents a request value object containing product information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestVO {

    @Schema(description = "The name of the product being saved or updated")
    @NotBlank
    @Size(max = 80)
    public String name;

    @Schema(description = "The description of the product being saved or updated")
    @NotBlank
    @Size(max = 255)
    public String description;

    @Schema(description = "The price of the product being saved or updated")
    @NotNull
    @PositiveOrZero
    public BigDecimal price;

    @Schema(description = "The flag representing if a product is active (true) or not (false)")
    @NotNull
    public Boolean active;

    @Schema(description = "The ID of the restaurant related to product being saved or updated")
    @NotNull
    @DecimalMin("1")
    public Long restaurantId;

}

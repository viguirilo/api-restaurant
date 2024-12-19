package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestVO {

    @NotBlank
    @Size(max = 80)
    public String name;

    @NotBlank
    @Size(max = 255)
    public String description;

    @NotNull
    @PositiveOrZero
    public BigDecimal price;

    @NotNull
    public Boolean active;

    @NotNull
    @DecimalMin("1")
    public Long restaurantId;

}

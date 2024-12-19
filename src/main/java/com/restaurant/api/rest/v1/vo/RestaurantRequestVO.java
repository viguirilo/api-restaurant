package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.core.validation.ValueZeroCheckField;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ValueZeroCheckField(referenceField = "shipRate", targetField = "name", mandatoryDescription = "Free shipping")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantRequestVO {

    @NotBlank
    @Size(max = 80)
    public String name;

    @NotNull
    @PositiveOrZero
    public BigDecimal shipRate;

    @NotNull
    public Boolean active;

    @NotNull
    public Boolean open;

    @NotNull
    @DecimalMin("1")
    public Long kitchenId;

    @NotBlank
    @Size(max = 100)
    public String addressStreetOrAvenue;

    @NotBlank
    @Size(max = 10)
    public String addressNumber;

    @Size(max = 50)
    public String addressComplement;

    @NotBlank
    @Size(max = 50)
    public String addressNeighborhood;

    @NotBlank
    @Size(max = 50)
    public String addressZipCode;

    @NotNull
    @DecimalMin("1")
    public Long addressCityId;

}

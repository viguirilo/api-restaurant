package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityRequestVO {

    @NotBlank
    @Size(max = 200)
    public String name;

    @NotNull
    @DecimalMin("1")
    public Long stateId;

}
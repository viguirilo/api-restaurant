package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityRequestVO {

    @NotBlank(message = "Name can not be null or blank")
    @NotEmpty(message = "Name can not bem null or empty")
    public String name;

    @NotNull(message = "StateId can not be null")
    public Long stateId;

}
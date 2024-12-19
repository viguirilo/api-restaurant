package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StateRequestVO {

    @NotBlank
    @Size(max = 80)
    public String name;

    @NotBlank
    @Size(max = 5)
    public String abbreviation;

    @NotBlank
    @Size(max = 25)
    public String country;

}

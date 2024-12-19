package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KitchenRequestVO {

    @NotBlank
    @Size(max = 100)
    public String name;

}

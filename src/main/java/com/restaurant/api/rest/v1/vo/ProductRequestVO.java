package com.restaurant.api.rest.v1.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestVO {

    public String name;
    public String description;
    public BigDecimal price;
    public Boolean active;
    public Long restaurantId;

}

package com.restaurant.api.rest.v1.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantRequestVO {

    public String name;
    public BigDecimal shipRate;
    public Boolean active;
    public Boolean open;
    public Long kitchenId;
    public String addressStreetOrAvenue;
    public String addressNumber;
    public String addressComplement;
    public String addressNeighborhood;
    public String addressZipCode;
    public Long addressCityId;

}

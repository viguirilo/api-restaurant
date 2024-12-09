package com.restaurant.api.rest.v1.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityRequestVO {

    public String name;
    public Long stateId;

}
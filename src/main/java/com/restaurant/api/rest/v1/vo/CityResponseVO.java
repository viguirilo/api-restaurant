package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityResponseVO {

    public Long id;
    public String name;
    public StateResponseVO state;

    public CityResponseVO(City city) {
        this.id = city.getId();
        this.name = city.getName();
        this.state = new StateResponseVO(city.getState());
    }

}

package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityResponseVO {

    public Long id;
    public String name;
    public State state;

    public CityResponseVO(City city) {
        BeanUtils.copyProperties(city, this);
    }

}

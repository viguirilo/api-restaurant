package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StateResponseVO {

    public Long id;
    public String name;
    public String abbreviation;
    public String country;

    public StateResponseVO(State state) {
        this.id = state.getId();
        this.name = state.getName();
        this.abbreviation = state.getAbbreviation();
        this.country = state.getCountry();
    }

}

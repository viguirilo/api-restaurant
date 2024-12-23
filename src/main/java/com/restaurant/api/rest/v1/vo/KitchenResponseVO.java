package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.Kitchen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KitchenResponseVO {

    public Long id;
    public String name;

    public KitchenResponseVO(Kitchen kitchen) {
        BeanUtils.copyProperties(kitchen, this);
    }

}

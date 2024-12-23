package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.Address;
import com.restaurant.api.rest.v1.entity.Kitchen;
import com.restaurant.api.rest.v1.entity.PaymentMethod;
import com.restaurant.api.rest.v1.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantResponseVO {

    public Long id;
    public String name;
    public BigDecimal shipRate;
    public Boolean active;
    public Boolean open;
    public LocalDateTime creationDate;
    public LocalDateTime updateDate;
    public Kitchen kitchen;
    public List<PaymentMethod> paymentMethods;
    public Address address;

    public RestaurantResponseVO(Restaurant restaurant) {
        BeanUtils.copyProperties(restaurant, this);
    }

}

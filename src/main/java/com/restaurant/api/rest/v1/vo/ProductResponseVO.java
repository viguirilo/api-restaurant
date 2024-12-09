package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.Product;
import com.restaurant.api.rest.v1.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseVO {

    public Long id;
    public String name;
    public String description;
    public BigDecimal price;
    public Boolean active;
    public Restaurant restaurant;

    public ProductResponseVO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.active = product.getActive();
        this.restaurant = product.getRestaurant();
    }

}

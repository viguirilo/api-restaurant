package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.Order;
import com.restaurant.api.rest.v1.entity.OrderedItem;
import com.restaurant.api.rest.v1.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderedItemResponseVO {

    private Long id;
    private Order order;
    private Product product;
    private String observation;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public OrderedItemResponseVO(OrderedItem orderedItem) {
        this.id = orderedItem.getId();
        this.order = orderedItem.getOrder();
        this.product = orderedItem.getProduct();
        this.observation = orderedItem.getObservation();
        this.quantity = orderedItem.getQuantity();
        this.unitPrice = orderedItem.getUnitPrice();
        this.totalPrice = orderedItem.getTotalPrice();
    }

}

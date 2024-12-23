package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.*;
import com.restaurant.api.rest.v1.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseVO {

    private Long id;
    private BigDecimal subTotal;
    private BigDecimal shipRate;
    private BigDecimal totalValue;
    private LocalDateTime creationDate;
    private LocalDateTime confirmationDate;
    private LocalDateTime cancellationDate;
    private LocalDateTime deliveryDate;
    private Restaurant restaurant;
    private User customer;
    private PaymentMethod paymentMethod;
    private Address address;
    private OrderStatus status;
    private List<OrderedItem> orderedItems = new ArrayList<>();

    public OrderResponseVO(Order order) {
        BeanUtils.copyProperties(order, this);
    }

}

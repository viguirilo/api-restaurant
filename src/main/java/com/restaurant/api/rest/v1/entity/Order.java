package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.enums.OrderStatus;
import com.restaurant.api.rest.v1.vo.OrderRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`order`")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "`order`")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "sub_total", nullable = false)
    @JsonProperty(value = "subTotal")
    private BigDecimal subTotal;

    @Column(name = "ship_rate", nullable = false)
    @JsonProperty(value = "shipRate")
    private BigDecimal shipRate;

    @Column(name = "total_value", nullable = false)
    @JsonProperty(value = "totalValue")
    private BigDecimal totalValue;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, columnDefinition = "datetime")
    @JsonProperty(value = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "confirmation_date", columnDefinition = "datetime")
    @JsonProperty(value = "confirmationDate")
    private LocalDateTime confirmationDate;

    @Column(name = "cancellation_date", columnDefinition = "datetime")
    @JsonProperty(value = "cancellationDate")
    private LocalDateTime cancellationDate;

    @Column(name = "delivery_date", columnDefinition = "datetime")
    @JsonProperty(value = "deliveryDate")
    private LocalDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonProperty(value = "restaurant")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonProperty(value = "customer")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    @JsonProperty(value = "paymentMethod")
    private PaymentMethod paymentMethod;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 25, nullable = false)
    @JsonProperty(value = "status")
    private OrderStatus status;

    @JsonIgnore // TODO(Por que tem JsonIgnore neste campo?)
    @OneToMany(mappedBy = "order")
    private List<OrderedItem> orderedItems = new ArrayList<>();

    public Order(OrderRequestVO orderRequestVO, Restaurant restaurant, User customer, PaymentMethod paymentMethod, City city) {
        this.subTotal = orderRequestVO.getSubTotal();
        this.shipRate = orderRequestVO.getShipRate();
        this.totalValue = orderRequestVO.getTotalValue();
        this.creationDate = LocalDateTime.now();
        this.restaurant = restaurant;
        this.customer = customer;
        this.paymentMethod = paymentMethod;
        this.address = new Address(orderRequestVO, city);
        this.status = OrderStatus.CREATED;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", subTotal=" + subTotal +
                ", shipRate=" + shipRate +
                ", totalValue=" + totalValue +
                ", creationDate=" + creationDate +
                ", confirmationDate=" + confirmationDate +
                ", cancellationDate=" + cancellationDate +
                ", deliveryDate=" + deliveryDate +
                ", restaurant=" + restaurant.toString() +
                ", customer=" + customer.toString() +
                ", paymentMethod=" + paymentMethod.toString() +
                ", address=" + address.toString() +
                ", status=" + status +
                ", itemsOrdered=" + orderedItems.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(creationDate, order.creationDate) &&
                Objects.equals(restaurant.getId(), order.restaurant.getId()) &&
                Objects.equals(customer.getId(), order.customer.getId()) &&
                Objects.equals(orderedItems, order.orderedItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, restaurant.getId(), customer.getId(), orderedItems);
    }

}

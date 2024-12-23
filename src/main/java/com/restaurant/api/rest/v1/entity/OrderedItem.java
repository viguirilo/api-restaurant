package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.OrderedItemRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ordered_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "ordered_item")
public class OrderedItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonProperty("order")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonProperty("product")
    private Product product;

    @Column(name = "observation", length = 255)
    @JsonProperty(value = "observation")
    private String observation;

    @Column(name = "quantity", nullable = false)
    @JsonProperty(value = "quantity")
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    @JsonProperty(value = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false)
    @JsonProperty(value = "total_price")
    private BigDecimal totalPrice;

    public OrderedItem(OrderedItemRequestVO orderedItemRequestVO, Order order, Product product) {
        this.order = order;
        this.product = product;
        this.observation = orderedItemRequestVO.getObservation();
        this.quantity = orderedItemRequestVO.getQuantity();
        this.unitPrice = orderedItemRequestVO.getUnitPrice();
        this.totalPrice = orderedItemRequestVO.getTotalPrice();
    }

    @Override
    public String toString() {
        return "ItemOrdered{" +
                "id=" + id +
                ", order=" + order.toString() +
                ", product=" + product.toString() +
                ", observation='" + observation + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedItem that = (OrderedItem) o;
        return Objects.equals(order.getId(), that.order.getId()) && Objects.equals(product.getId(), that.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(order.getId(), product.getId());
    }

}

package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "item_ordered")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "item_ordered")
public class ItemOrdered implements Serializable {

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

    @Column(name = "quantity", nullable = false)
    @JsonProperty(value = "quantity")
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    @JsonProperty(value = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false)
    @JsonProperty(value = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "observation")
    @JsonProperty(value = "observation")
    private String observation;

    @Override
    public String toString() {
        return "ItemOrdered{" +
                "id=" + id +
                ", order=" + order.toString() +
                ", product=" + product.toString() +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", observation='" + observation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOrdered that = (ItemOrdered) o;
        return Objects.equals(order.getId(), that.order.getId()) && Objects.equals(product.getId(), that.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(order.getId(), product.getId());
    }

}

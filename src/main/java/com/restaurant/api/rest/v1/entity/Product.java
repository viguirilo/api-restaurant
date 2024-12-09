package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.ProductRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "description", nullable = false)
    @JsonProperty(value = "description")
    private String description;

    @Column(name = "price", nullable = false)
    @JsonProperty(value = "price")
    private BigDecimal price;

    @Column(name = "active", nullable = false)
    @JsonProperty(value = "active")
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonProperty("restaurant")
    private Restaurant restaurant;

    public Product(ProductRequestVO productRequestVO, Restaurant restaurant) {
        this.name = productRequestVO.getName();
        this.description = productRequestVO.getDescription();
        this.price = productRequestVO.getPrice();
        this.active = productRequestVO.getActive();
        this.restaurant = restaurant;
    }

}

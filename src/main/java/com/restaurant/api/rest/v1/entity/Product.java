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
import java.util.Objects;

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

    @Column(name = "name", length = 80, nullable = false)
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "description", length = 255, nullable = false)
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
        this.name = productRequestVO.getName().trim().replaceAll("\\s+", " ");
        this.description = productRequestVO.getDescription().trim().replaceAll("\\s+", " ");
        this.price = productRequestVO.getPrice();
        this.active = productRequestVO.getActive();
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", active=" + active +
                ", restaurant=" + restaurant.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(restaurant, product.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, restaurant);
    }

}

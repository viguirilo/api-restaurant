package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "restaurant")
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @JsonProperty(value = "name")
    private String name;

    @Column(name = "ship_rate", nullable = false)
    @JsonProperty(value = "shipRate")
    private BigDecimal shipRate;

    @Column(name = "active", nullable = false)
    @JsonProperty(value = "active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "open", nullable = false)
    @JsonProperty(value = "open")
    private Boolean open = Boolean.TRUE;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, columnDefinition = "datetime")
    @JsonProperty(value = "creationDate")
    private LocalDateTime creationDate;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "update_date", nullable = false, columnDefinition = "datetime")
    @JsonProperty(value = "updateDate")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kitchen_id", nullable = false)
    @JsonProperty(value = "kitchen")
    private Kitchen kitchen;

    //    Colocamos jsonignore pois o payload das formas de pagamento pode ser muito grande em uma busca de findAll
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id")
    )
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public Restaurant(RestaurantRequestVO restaurantRequestVO, Kitchen kitchen, City city) {
        this.name = restaurantRequestVO.getName();
        this.shipRate = restaurantRequestVO.getShipRate();
        this.active = restaurantRequestVO.getActive();
        this.open = restaurantRequestVO.getOpen();
        this.creationDate = restaurantRequestVO.getCreationDate();
        this.updateDate = restaurantRequestVO.getUpdateDate();
        this.kitchen = kitchen;
        this.address = new Address(restaurantRequestVO, city);
    }

}

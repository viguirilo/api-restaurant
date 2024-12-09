package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Column(name = "address_street_or_avenue", nullable = false)
    @JsonProperty(value = "addressStreetOrAvenue")
    private String addressStreetOrAvenue;

    @Column(name = "address_number", nullable = false)
    @JsonProperty(value = "addressNumber")
    private String addressNumber;

    @Column(name = "address_complement")
    @JsonProperty(value = "addressComplement")
    private String addressComplement;

    @Column(name = "address_neighborhood", nullable = false)
    @JsonProperty(value = "addressNeighborhood")
    private String addressNeighborhood;

    @Column(name = "address_zip_code", nullable = false)
    @JsonProperty(value = "addressZipCode")
    private String addressZipCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_city_id")
    private City city;

    public Address(RestaurantRequestVO restaurantRequestVO, City city) {
        this.addressStreetOrAvenue = restaurantRequestVO.getAddressStreetOrAvenue();
        this.addressNumber = restaurantRequestVO.getAddressNumber();
        this.addressComplement = restaurantRequestVO.getAddressComplement();
        this.addressNeighborhood = restaurantRequestVO.getAddressNeighborhood();
        this.addressZipCode = restaurantRequestVO.getAddressZipCode();
        this.city = city;
    }

}

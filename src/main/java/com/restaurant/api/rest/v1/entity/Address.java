package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Column(name = "address_street_or_avenue", length = 100, nullable = false)
    @JsonProperty(value = "addressStreetOrAvenue")
    private String addressStreetOrAvenue;

    @Column(name = "address_number", length = 10, nullable = false)
    @JsonProperty(value = "addressNumber")
    private String addressNumber;

    @Column(name = "address_complement", length = 50)
    @JsonProperty(value = "addressComplement")
    private String addressComplement;

    @Column(name = "address_neighborhood", length = 50, nullable = false)
    @JsonProperty(value = "addressNeighborhood")
    private String addressNeighborhood;

    @Column(name = "address_zip_code", length = 25, nullable = false)
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

    @Override
    public String toString() {
        return "Address{" +
                "addressStreetOrAvenue='" + addressStreetOrAvenue + '\'' +
                ", addressNumber='" + addressNumber + '\'' +
                ", addressComplement='" + addressComplement + '\'' +
                ", addressNeighborhood='" + addressNeighborhood + '\'' +
                ", addressZipCode='" + addressZipCode + '\'' +
                ", city=" + city.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressStreetOrAvenue, address.addressStreetOrAvenue) &&
                Objects.equals(addressNumber, address.addressNumber) &&
                Objects.equals(addressComplement, address.addressComplement) &&
                Objects.equals(addressNeighborhood, address.addressNeighborhood) &&
                Objects.equals(addressZipCode, address.addressZipCode) &&
                Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                addressStreetOrAvenue,
                addressNumber,
                addressComplement,
                addressNeighborhood,
                addressZipCode,
                city
        );
    }

}

package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.PaymentMethodRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "payment_method")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "paymentMethod")
public class PaymentMethod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "description", length = 50, nullable = false)
    @JsonProperty(value = "description")
    private String description;

    public PaymentMethod(PaymentMethodRequestVO paymentMethodRequestVO) {
        this.description = paymentMethodRequestVO.getDescription().trim().replaceAll("\\s+", " ");
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }

}

package com.restaurant.api.rest.v1.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderedItemRequestVO {

    @NotNull
    @DecimalMin("1")
    Long orderId;

    @NotNull
    @DecimalMin("1")
    private Long productId;

    @Size(max = 255)
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

}

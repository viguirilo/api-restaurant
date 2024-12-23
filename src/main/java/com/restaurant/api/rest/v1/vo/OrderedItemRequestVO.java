package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
    @Min(1)
    private Long productId;

    @Size(max = 255)
    private String observation;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal totalPrice;

}

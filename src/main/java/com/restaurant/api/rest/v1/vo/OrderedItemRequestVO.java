package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Represents a request value object containing ordered item information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderedItemRequestVO {

    @Schema(description = "The ID of the order related to ordered item being saved or updated")
    @NotNull
    @DecimalMin("1")
    Long orderId;

    @Schema(description = "The ID of the product related to ordered item being saved or updated")
    @NotNull
    @Min(1)
    private Long productId;

    @Schema(description = "The observation of the ordered item being saved or updated")
    @Size(max = 255)
    private String observation;

    @Schema(description = "The quantity of the ordered item being saved or updated")
    @NotNull
    @Min(1)
    private Integer quantity;

    @Schema(description = "The unit price of the ordered item being saved or updated")
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    @Schema(description = "The total price of the ordered item being saved or updated")
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal totalPrice;

}

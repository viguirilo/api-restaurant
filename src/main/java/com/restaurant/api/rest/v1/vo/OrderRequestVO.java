package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Represents a request value object containing order information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestVO {

    @Schema(description = "The address street or avenue of the order being saved or updated")
    @NotBlank
    @Size(max = 100)
    public String addressStreetOrAvenue;
    @Schema(description = "The address number of the order being saved or updated")
    @NotBlank
    @Size(max = 10)
    public String addressNumber;
    @Schema(description = "The address complement of the order being saved or updated")
    @Size(max = 50)
    public String addressComplement;
    @Schema(description = "The address neighborhood of the order being saved or updated")
    @NotBlank
    @Size(max = 50)
    public String addressNeighborhood;
    @Schema(description = "The address zip code of the order being saved or updated")
    @NotBlank
    @Size(max = 50)
    public String addressZipCode;
    @Schema(description = "The address city ID of the order being saved or updated")
    @NotNull
    @Min(1)
    public Long addressCityId;
    @Schema(description = "The subtotal of the order being saved or updated")
    @NotNull
    @Min(0)
    private BigDecimal subTotal;
    @Schema(description = "The ship rate of the order being saved or updated")
    @NotNull
    @Min(0)
    private BigDecimal shipRate;
    @Schema(description = "The total value of the order being saved or updated")
    @NotNull
    @Min(0)
    private BigDecimal totalValue;
    @Schema(description = "The creation date of the order being saved or updated")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime creationDate;
    @Schema(description = "The confirmation date of the order being saved or updated")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime confirmationDate;
    @Schema(description = "The cancellation date of the order being saved or updated")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime cancellationDate;
    @Schema(description = "The delivery date of the order being saved or updated")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime deliveryDate;
    @Schema(description = "The ID of the restaurant related to ordered being saved or updated")
    @NotNull
    @Min(1)
    private Long restaurantId;
    @Schema(description = "The ID of the customer related to ordered being saved or updated")
    @NotNull
    @Min(1)
    private Long customerId;
    @Schema(description = "The ID of the payment method related to ordered being saved or updated")
    @NotNull
    @Min(1)
    private Long paymentMethodId;

}

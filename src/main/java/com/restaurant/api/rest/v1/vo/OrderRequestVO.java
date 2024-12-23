package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestVO {

    @NotNull
    @Min(0)
    private BigDecimal subTotal;

    @NotNull
    @Min(0)
    private BigDecimal shipRate;

    @NotNull
    @Min(0)
    private BigDecimal totalValue;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime creationDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime confirmationDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime cancellationDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime deliveryDate;

    @NotNull
    @Min(1)
    private Long restaurantId;

    @NotNull
    @Min(1)
    private Long customerId;

    @NotNull
    @Min(1)
    private Long paymentMethodId;

    @NotBlank
    @Size(max = 100)
    public String addressStreetOrAvenue;

    @NotBlank
    @Size(max = 10)
    public String addressNumber;

    @Size(max = 50)
    public String addressComplement;

    @NotBlank
    @Size(max = 50)
    public String addressNeighborhood;

    @NotBlank
    @Size(max = 50)
    public String addressZipCode;

    @NotNull
    @Min(1)
    public Long addressCityId;

}

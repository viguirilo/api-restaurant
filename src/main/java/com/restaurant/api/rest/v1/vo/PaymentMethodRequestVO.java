package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a request value object containing payment method information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethodRequestVO {

    @Schema(description = "The description of the payment method being saved or updated")
    @NotBlank
    @Size(max = 50)
    private String description;

}

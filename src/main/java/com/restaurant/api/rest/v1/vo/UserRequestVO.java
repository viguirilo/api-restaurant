package com.restaurant.api.rest.v1.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a request value object containing user information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestVO {

    @Schema(description = "The name of the user being saved or updated")
    @NotBlank
    @Size(max = 80)
    public String fullname;

    @Schema(description = "The email of the user being saved or updated")
    @NotBlank
    @Email
    @Size(max = 50)
    public String email;

    @Schema(description = "The password of the user being saved or updated")
    @Size(max = 100)
    public String password;

    @Schema(description = "The language code of the user being saved or updated")
    @NotBlank
    @Size(max = 5)
    public String languageCode;

    @Schema(description = "The currency code of the user being saved or updated")
    @NotBlank
    @Size(max = 3)
    public String currencyCode;

    @Schema(description = "The timezone of the user being saved or updated")
    @NotBlank
    @Size(max = 6)
    public String timezone;

    @Schema(description = "The username of the user being saved or updated")
    @NotBlank
    @Size(max = 50)
    private String username;

}

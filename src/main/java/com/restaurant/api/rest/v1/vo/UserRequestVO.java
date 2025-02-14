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
    public String name;

    @Schema(description = "The email of the user being saved or updated")
    @NotBlank
    @Email
    @Size(max = 50)
    public String email;

    @Schema(description = "The password of the user being saved or updated")
    @Size(max = 100)
    public String password;

}

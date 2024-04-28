package net.mahamanjari.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.stereotype.Component;

@Schema(
        description = "UserDto Model information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDto {

    private Long id;

    @Schema(
            description = "User First Name"
    )
    @NotEmpty(message = "User first name should not be null or empty") //custom validation error message
    private String firstName;

    @Schema(
            description = "User Last Name"
    )
    @NotEmpty(message = "User last name should not be null or empty")
    private String lastName;

    @Schema(
            description = "User Email Address"
    )
    @Email(message = "User email should not be null or empty")
    @NotEmpty(message = "email address should be valid")
    private String mail;
}

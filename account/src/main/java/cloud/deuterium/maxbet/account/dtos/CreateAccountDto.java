package cloud.deuterium.maxbet.account.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Milan Stojkovic 04-Apr-2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDto {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;
}

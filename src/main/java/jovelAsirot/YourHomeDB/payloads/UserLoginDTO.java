package jovelAsirot.YourHomeDB.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(@NotEmpty(message = "The email is required")
                           @Email(message = "The email given is invalid")
                           String email,
                           @NotEmpty(message = "The password is required")
                           @Size(min = 8, message = "The password can't be less than eight characters characters")
                           String password) {
}

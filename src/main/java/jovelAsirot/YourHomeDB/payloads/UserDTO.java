package jovelAsirot.YourHomeDB.payloads;

import jakarta.validation.constraints.*;

import java.util.List;

public record UserDTO(@NotEmpty(message = "Required field: name")
                      @Size(min = 2, max = 30, message = "The name can't be less than two characters and more than 30 characters")
                      String name,
                      @NotEmpty(message = "Required field: surname")
                      @Size(min = 2, max = 30, message = "The surname can't be less than two characters and more than 30 characters")
                      String surname,
                      @NotEmpty(message = "Required field: email")
                      @Email(message = "The email given is invalid")
                      String email,
                      @NotEmpty(message = "Required field: password")
                      @Size(min = 8, message = "The password can't be less than eight characters characters")
                      String password,
                      String role,
                      @NotNull(message = "Required field: birthdate")
                      @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use 'YYYY-MM-DD' ")
                      String birthdate,
                      @NotEmpty(message = "Required field: country")
                      String country,
                      @NotEmpty(message = "Required field: phone")
                      String phone,
                      List<Long> favoritePropertyIds
) {
}

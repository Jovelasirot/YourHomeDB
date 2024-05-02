package jovelAsirot.YourHomeDB.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PropertyDTO(@NotEmpty(message = "The address is required")
                          String address,
                          @NotNull(message = "The price is required")
                          int price,
                          @NotNull(message = "The area is required")
                          double area,
                          @NotNull(message = "The number of bedrooms is required")
                          int bedrooms,
                          @NotNull(message = "The number of bathroom is required")
                          int bathroom,
                          @NotEmpty(message = "Property type is required")
                          @Pattern(regexp = "HOUSE|APARTMENT|CONDO|LAND", message = "Invalid role input, choose between HOUSE | APARTMENT | CONDO | LAND")
                          String propertyType,
                          @NotEmpty(message = "Property type is required")
                          @Pattern(regexp = "NEW|RESTORED|TO_BE_RESTORED", message = "Invalid role input, choose between NEW | RESTORED | TO_BE_RESTORED")
                          String propertyStatus) {
}

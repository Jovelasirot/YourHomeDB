package jovelAsirot.YourHomeDB.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PropertyDTO(@NotEmpty(message = "Required field: city")
                          String city,
                          @NotEmpty(message = "Required field: country")
                          String country,
                          @NotEmpty(message = "Required field: address")
                          String address,
                          @NotNull(message = "Required field: price")
                          int price,
                          @NotNull(message = "Required field: area of property")
                          double area,
                          @NotNull(message = "Required field:  bedrooms count")
                          int bedrooms,
                          @NotNull(message = "Required field: bathroom count")
                          int bathroom,
                          @NotEmpty(message = "Required field: property type")
                          @Pattern(regexp = "HOUSE|APARTMENT|CONDO|LAND", message = "Invalid Property type input, choose between HOUSE | APARTMENT | CONDO | LAND")
                          String propertyType,
                          @NotEmpty(message = "Required field: property status")
                          @Pattern(regexp = "NEW|RESTORED|TO_BE_RESTORED", message = "Invalid Property status input, choose between NEW | RESTORED | TO_BE_RESTORED")
                          String propertyStatus,
                          @NotEmpty(message = "Required field: description")
                          String description,
                          Long userId) {
}

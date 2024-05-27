package jovelAsirot.YourHomeDB.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservationDTO(Long propertyId,
                             Long userId,
                             @NotNull(message = "Required field: date")
                             @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Please use 'YYYY-MM-DD' ")
                             String reservationDate,
                             String time,
                             String reservationStatus

) {
}

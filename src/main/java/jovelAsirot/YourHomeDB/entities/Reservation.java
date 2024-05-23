package jovelAsirot.YourHomeDB.entities;

import jakarta.persistence.*;
import jovelAsirot.YourHomeDB.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private LocalDate reservationDate;

    private String time;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Reservation(User user, Property property, String reservationDate, String time, String reservationStatus) {
        this.user = user;
        this.property = property;
        this.reservationDate = LocalDate.parse(reservationDate);
        this.time = time;
        this.reservationStatus = ReservationStatus.valueOf(reservationStatus);
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

}

package jovelAsirot.YourHomeDB.repositories;

import jovelAsirot.YourHomeDB.entities.Property;
import jovelAsirot.YourHomeDB.entities.Reservation;
import jovelAsirot.YourHomeDB.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDAO extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByUser(User user, Pageable pageable);

    Page<Reservation> findByProperty(Property property, Pageable pageable);
}

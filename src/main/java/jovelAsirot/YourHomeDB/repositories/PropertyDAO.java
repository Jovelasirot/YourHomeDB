package jovelAsirot.YourHomeDB.repositories;

import jovelAsirot.YourHomeDB.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyDAO extends JpaRepository<Property, Long> {
}

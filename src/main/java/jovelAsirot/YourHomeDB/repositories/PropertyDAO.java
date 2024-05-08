package jovelAsirot.YourHomeDB.repositories;

import jovelAsirot.YourHomeDB.entities.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyDAO extends JpaRepository<Property, Long> {
    Page<Property> findAll(Specification<Property> spec, Pageable pageable);
}

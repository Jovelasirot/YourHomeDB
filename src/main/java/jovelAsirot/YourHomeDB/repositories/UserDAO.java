package jovelAsirot.YourHomeDB.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import jovelAsirot.YourHomeDB.entities.User;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndId(String email, Long userId);
    
}

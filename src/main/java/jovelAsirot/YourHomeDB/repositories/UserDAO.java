package jovelAsirot.YourHomeDB.repositories;

import jovelAsirot.YourHomeDB.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndId(String email, Long userId);

    Optional<User> findByPhone(String phone);

}

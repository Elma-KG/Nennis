package is.hi.hbv501g.nennis.Persistence.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import is.hi.hbv501g.nennis.Persistence.Entities.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    default Optional<User> findByUsernameOrEmail(String identifier) {
        if (identifier == null) return Optional.empty();
        if (identifier.contains("@")) return findByEmail(identifier);

        return findByUsername(identifier);

    }
}

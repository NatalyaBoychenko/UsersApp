package springframework.usersapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springframework.usersapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

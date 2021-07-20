package apps.gam.web.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apps.gam.web.backend.models.User;

public interface UserRepository extends JpaRepository<User, String> {
	@Query(value = "SELECT u.ID FROM gamemanager.user u WHERE u.ID = ?1 and u.Password = ?2", nativeQuery = true)
	String userValid(String id, int pwd);
}

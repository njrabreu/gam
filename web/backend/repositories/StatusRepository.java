package apps.gam.web.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apps.gam.web.backend.models.Status;

public interface StatusRepository extends JpaRepository<Status, String> {
	@Query(value = "SELECT concat(ID, \" - \", Description) rate FROM gamemanager.status order by ID", nativeQuery = true)
	List<String> getStatuses();
}

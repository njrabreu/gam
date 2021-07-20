package apps.gam.web.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apps.gam.web.backend.models.Platform;

public interface PlatformRepository extends JpaRepository<Platform, String> {
	@Query(value = "SELECT concat(ID, \" - \", Description) platform FROM gamemanager.platform order by ID", nativeQuery = true)
	List<String> getPlatforms();
}

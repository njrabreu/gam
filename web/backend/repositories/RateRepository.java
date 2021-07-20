package apps.gam.web.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import apps.gam.web.backend.models.Rate;

public interface RateRepository extends JpaRepository<Rate, String> {
	@Query(value = "SELECT concat(ID, \" - \", Description) rate FROM gamemanager.rate order by ID", nativeQuery = true)
	List<String> getRates();
}

package apps.gam.web.backend.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import apps.gam.web.backend.models.Game;

public interface GameRepository extends JpaRepository<Game, Integer> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE gamemanager.game SET Title = ?2, Completion = ?3, Platform = ?4, Rate = ?5, Status = ?6 WHERE ID = ?1", nativeQuery = true)
	void updateGame(int id, String title, Date completion, int platform, int rate, int status);
}

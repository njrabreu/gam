package apps.gam.web.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import apps.gam.web.backend.models.All_Games;

public interface AllGamesRepository extends JpaRepository<All_Games, Integer> {

}

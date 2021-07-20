package apps.gam.web.backend.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import apps.gam.desktop.core.MyLogger;
import apps.gam.web.backend.models.All_Games;
import apps.gam.web.backend.models.Game;
import apps.gam.web.backend.repositories.AllGamesRepository;
import apps.gam.web.backend.repositories.GameRepository;
import apps.gam.web.backend.repositories.PlatformRepository;
import apps.gam.web.backend.repositories.RateRepository;
import apps.gam.web.backend.repositories.StatusRepository;
import apps.gam.web.backend.security.JwtTokenUtil;

@RestController
@RequestMapping("/game")
public class GameController {
	@Autowired
	private AllGamesRepository allGamesRepository;
	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private PlatformRepository platformRepository;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	private void printHeaders(Map<String, String> headers) {
		MyLogger.WriteMessage("User is: " + headers.get("id"));
		MyLogger.WriteMessage("Token is: " + headers.get("token"));
	}

	@GetMapping("/all")
	public List<All_Games> list(@RequestHeader Map<String, String> headers, HttpServletResponse response) {
		MyLogger.WriteMessage("Showing all games via web!");
		printHeaders(headers);

		List<All_Games> list = null;

		String userId = headers.get("id");
		String token = headers.get("token");

		try {

			if (token != null && userId != null && jwtTokenUtil.validateToken(token, userId))
				list = allGamesRepository.findAll();
			else
			{
				MyLogger.WriteMessage("Invalid Authorization");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}

		} catch (Exception e) {
			MyLogger.WriteMessage("Invalid Authorization");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

		return list;
	}

	@GetMapping("/{id}")
	public Game get(@PathVariable("id") int id, @RequestHeader Map<String, String> headers, HttpServletResponse response) {
		MyLogger.WriteMessage("Showing game ID " + id + " via web!");
		printHeaders(headers);

		Game game = null;

		String userId = headers.get("id");
		String token = headers.get("token");

		try {

			if (token != null && userId != null && jwtTokenUtil.validateToken(token, userId))
				game = gameRepository.getById(id);
			else
			{
				MyLogger.WriteMessage("Invalid Authorization");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
			
		} catch (Exception e) {
			MyLogger.WriteMessage("Invalid Authorization");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}

		return game;
	}

	@GetMapping("/platforms")
	public String platforms() {
		MyLogger.WriteMessage("Showing all platforms via web!");
		return new Gson().toJson(platformRepository.getPlatforms());
	}

	@GetMapping("/rates")
	public String rates() {
		MyLogger.WriteMessage("Showing all rates via web!");
		return new Gson().toJson(rateRepository.getRates());
	}

	@GetMapping("/statuses")
	public String statuses() {
		MyLogger.WriteMessage("Showing all statuses via web!");
		return new Gson().toJson(statusRepository.getStatuses());
	}

	@PostMapping("/new")
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestBody Game g, @RequestHeader Map<String, String> headers, HttpServletResponse response) {
		MyLogger.WriteMessage("Registering game " + g.getId() + " via web!");
		printHeaders(headers);

		String userId = headers.get("id");
		String token = headers.get("token");

		try {

			if (token != null && userId != null && jwtTokenUtil.validateToken(token, userId))
				gameRepository.save(g);
			else
			{
				MyLogger.WriteMessage("Invalid Authorization");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}

		} catch (Exception e) {
			MyLogger.WriteMessage("Invalid Authorization");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@PostMapping("/update")
	@ResponseStatus(HttpStatus.OK)
	public void update(@RequestBody Game g, @RequestHeader Map<String, String> headers, HttpServletResponse response) {
		MyLogger.WriteMessage("Updating game " + g.getId() + " via web!");
		printHeaders(headers);

		String userId = headers.get("id");
		String token = headers.get("token");

		try {

			if (token != null && userId != null && jwtTokenUtil.validateToken(token, userId))
				gameRepository.updateGame(g.getId(), g.getTitle(), g.getCompletion(), g.getPlatform(), g.getRate(),
						g.getStatus());
			else
			{
				MyLogger.WriteMessage("Invalid Authorization");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}

		} catch (Exception e) {
			MyLogger.WriteMessage("Invalid Authorization");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
}

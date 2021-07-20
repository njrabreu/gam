package apps.gam.web.backend.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import apps.gam.desktop.core.MyLogger;
import apps.gam.web.backend.models.User;
import apps.gam.web.backend.repositories.UserRepository;
import apps.gam.web.backend.security.JwtTokenUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping("/check")
	public String check(@RequestParam Map<String, String> request) {
		String userID = request.get("id");
		String userPWD = request.get("pwd");
		String token = null;

		MyLogger.WriteMessage("Checking user " + userID + " via web!");

		if (userRepository.userValid(userID, userPWD.hashCode()) != null) {
			token = jwtTokenUtil.generateToken(userID);
		} else {
			userID = null;
			token = null;
			MyLogger.WriteMessage("Invalid user!");
		}

		// Answer must be in JSON format
		return "{ \"id\": \"" + userID + "\", \"" + "token\": \"" + token + "\"" + " }";
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public void create(@RequestParam Map<String, String> request) {
		String userID = request.get("id");
		String userPWD = request.get("pwd");

		User u = new User();
		u.setId(userID);
		u.setPassword(userPWD.hashCode());

		MyLogger.WriteMessage("Registering user " + userID + " via web!");

		userRepository.save(u);
	}
}

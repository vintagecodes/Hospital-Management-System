package sec.Controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sec.payLoad.LoginRequest;
import sec.payLoad.SignupRequest;
import sec.payLoad.JwtResponse;
import sec.model.User;
import sec.model.service.AuthService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	
	@Autowired
	private AuthService authService;

	@Autowired
	private JwtResponse jwt;
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		return authService.authenticateUser(loginRequest);
		
		
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@jakarta.validation.Valid @RequestBody SignupRequest signUpRequest) throws CustomException, Exception {
		
		return authService.registerUser(signUpRequest);
	}
	
	@GetMapping("/users")
	public List<User> getUsers(){
		return authService.getAllUsers();
	}
	
	@GetMapping("/view/{username}")
	public Optional<User> getByUsername(@PathVariable String username){
		return authService.getDetailsByUsername(username);
	}
	
	@DeleteMapping("/delete/{username}")
	public void deleteUsers(@PathVariable("username") String username) {
		authService.deleteUsers(username);
	}
//	
//	@PostMapping("/logout")
//	public LogoutConfigurer<HttpSecurity> logout(HttpSecurity http) throws Exception{
//		return authService.logout(http);
//	}
	
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

	@PostMapping("/logout")
	public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
	    // .. perform logout
	    this.logoutHandler.logout(request, response, authentication);
	    return "User Logged out";
	}

}
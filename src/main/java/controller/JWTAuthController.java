package controller;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import beans.jwt.AuthenticationBean;
import beans.jwt.MinimalJWTUser;
import model.User;
import services.security.jwt.JWTService;
import services.user.UserService;

@RestController
public class JWTAuthController {

    @Autowired
    private JWTService jwtService;
	
	@Autowired
	private UserService userService;
	
    @PostMapping(value = "/api/public/auth")
    public ResponseEntity<?> auth(@RequestBody AuthenticationBean auth, HttpServletResponse response) {
    	
        String username = auth.getUsername();
        String password = auth.getPassword();
        
        Optional<User> user = this.userService.findUserByUsernameAndPassword(username, password);        		
        
        if (user.isPresent()) {
        	
            MinimalJWTUser jwtUser = new MinimalJWTUser(user.get().getUsername(), 
            		user.get()
                	.getUsersRoleses()
                	.stream()
                	.map(u -> u.getRole())
                	.collect(Collectors.toSet()));
            
            return ResponseEntity.ok(jwtService.getToken(jwtUser));
        }        
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

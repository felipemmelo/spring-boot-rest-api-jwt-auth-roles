package services.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.User;
import repository.UserRepository;
import services.security.password.PasswordService;
 
@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;
     
    @Autowired
    private PasswordService passwordService;
    
    public User findUserByUserName(String username) {
        return this.userRepository.findOne(username);
    }
 
    public boolean authenticate(String username, String password) {
    	
        User user = findUserByUserName(username);
        
        return this.passwordService.equals(password, user.getPassword());
    }
    
    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
    	
    	User user = this.findUserByUserName(username);
    	
    	return this.passwordService.equals(password, user.getPassword()) ? Optional.of(user) : Optional.empty();    	
    }
}

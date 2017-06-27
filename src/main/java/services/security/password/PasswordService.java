package services.security.password;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import constants.Parameters;

@Configuration
@Service
public class PasswordService {

    @Value(Parameters.KEY_JWT_SECRET)
    private String secret;
	
    private PasswordEncoder passwordEncoder;
    
	@PostConstruct
    public void init() {
    	this.passwordEncoder = new StandardPasswordEncoder(this.secret);
	}	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return this.passwordEncoder;
	}
	
	public String encode(String rawPassword) {
		return this.passwordEncoder.encode(rawPassword);
	}
	
	public boolean equals(String rawPassword, String encodedPassword) {
		
		return this.passwordEncoder.matches(rawPassword, encodedPassword);
	}
}

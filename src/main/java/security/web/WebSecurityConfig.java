package security.web;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import constants.Parameters;
import security.JWTFilter;
import services.security.jwt.JWTService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JWTService jwtTokenService;
 
    @Value(Parameters.KEY_JWT_AUTH_HEADER)
    String authHeader;

    @Value(Parameters.KEY_SECURITY_OPEN_API_PATTERN)
    String openApiPattern;

    @Value(Parameters.KEY_SECURITY_SECURE_API_PATTERN)
    String secureApiPattern;
    
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers(this.openApiPattern);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {				
		
		http
			.csrf()
				.disable()			
			.authorizeRequests()				
				.antMatchers(this.secureApiPattern)
					.hasRole("ADMIN")
				.and()				
				.addFilterBefore(new JWTFilter(this.secureApiPattern, jwtTokenService, authHeader), UsernamePasswordAuthenticationFilter.class);		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
		  .usersByUsernameQuery("select username,password, enabled from users where username=?")
		  .authoritiesByUsernameQuery("select username, role from user_roles where username=?")
		  .passwordEncoder(this.passwordEncoder);
	}
}

package beans.jwt;

import java.util.Set;

/**
 * This class represents a logged JWT user's credentials.
 *
 */
public class MinimalJWTUser {

	private String username;
	private Set<String> roles;
	
	public MinimalJWTUser() {}
	
	public MinimalJWTUser(String username, Set<String> roles) {
		super();
		this.username = username;
		this.roles = roles;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}

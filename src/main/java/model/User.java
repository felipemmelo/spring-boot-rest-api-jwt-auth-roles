package model;
// Generated Jun 23, 2017 3:57:42 PM by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 8167779603327526607L;
	private String username;
	private String password;
	private int id;
	private Set<UsersRoles> usersRoleses = new HashSet<UsersRoles>(0);

	public User() {
	}

	public User(String username, String password, int id) {
		this.username = username;
		this.password = password;
		this.id = id;
	}

	public User(String username, String password, int id, Set<UsersRoles> usersRoleses) {
		this.username = username;
		this.password = password;
		this.id = id;
		this.usersRoleses = usersRoleses;
	}

	@Id

	@Column(name = "username", unique = true, nullable = false, length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 250)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "id", nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<UsersRoles> getUsersRoleses() {
		return this.usersRoleses;
	}

	public void setUsersRoleses(Set<UsersRoles> usersRoleses) {
		this.usersRoleses = usersRoleses;
	}

}

package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.User;

/**
 * This class defines a repository for accessing {@link User}s data. 
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{

	public User findByUsernameAndPassword(String username, String password);
}

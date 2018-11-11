package daos;

import models.User;

public interface TestUserDao {

	/**
	 * Retrieves a user instance from a provided username, 
	 * or null if no user with the provided username.
	 * @param username
	 * @return
	 */
		User getUserByUsername (String username);
}
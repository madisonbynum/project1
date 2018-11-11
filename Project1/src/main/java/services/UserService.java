package services;

import java.sql.SQLException;
import java.util.List;

import daos.TestUserDao;
import daos.UserDao;
import models.TestCredentials;
import models.User;
import util.BadRequestException;
import util.NotFoundException;

public class UserService {

	
	private TestUserDao TestUserDao;
	UserDao userDao = new UserDao();
	
	public UserService(daos.TestUserDao mockUserDao) {
		// TODO Auto-generated constructor stub
	}

	public UserService() {
		// TODO Auto-generated constructor stub
	}

	public String authenticate(TestCredentials credentials) {
		if (credentials.getUsername() == null || credentials.getPassword() == null) {
			throw new BadRequestException();
		}

		User user = TestUserDao.getUserByUsername(credentials.getUsername());
		if (user == null)
			throw new NotFoundException();
		if (!user.getPassword().equals(credentials.getPassword())) {
			throw new NotFoundException();
		}

		return "token";

	}
	
	public User getUserByUnPw(String username, String password) throws SQLException {
		System.out.println("Got to the userservice");
		if(username == null || password == null) {
			throw new BadRequestException();
		}
		System.out.println("before method call");
		User user = userDao.getUserByUnPw(username, password);
		System.out.println("userservice method call");
		if(user == null)
				throw new NotFoundException();
		if(!user.getUsername().equals(username)|| !user.getPassword().equals(password)) {
			throw new NotFoundException();
		}
		
		return user;
	}

	
	public User getUserById (int id) {
		return userDao.getUserById(id);
	}
	
	public List<User> getUserByFullName (String firstname, String lastname) {
		return userDao.getUserByFullName(firstname, lastname);
	}
}

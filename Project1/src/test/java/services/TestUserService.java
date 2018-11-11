package services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import daos.TestUserDao;
import models.TestCredentials;
import models.User;
import util.BadRequestException;
import util.NotFoundException;


public class TestUserService {
	
	//Mock implementation
			TestUserDao mockUserDao = mock(TestUserDao.class);
			UserService userService = new UserService(mockUserDao);

	//Test driven developement
			//Tests are red until they're green
			@Test(expected = BadRequestException.class)
			public void authenticateNoUsernameTest() throws Exception{
				TestCredentials credentials = new TestCredentials(null, "password");
				userService.authenticate(credentials);
			}

			@Test(expected = BadRequestException.class)
			public void authenticateNoPasswordTest() throws Exception{
				TestCredentials credentials = new TestCredentials("username", null);
				userService.authenticate(credentials);
			}
			
			@Test(expected = NotFoundException.class)
			public void authenticateNoUserWithUsername() throws Exception{
				/**
				 * When the userService calls the getUserByUsername method on the dao
				 * with the String "Not Found" the dao will return null.
				 * This is an example of a stub method.
				 */
				when(mockUserDao.getUserByUsername("NotFound")).thenReturn(null);
				when(mockUserDao.getUserByUsername("password")).thenThrow(new RuntimeException());
				TestCredentials credentials = new TestCredentials("NotFound", "password");
				userService.authenticate(credentials);
			}
			
			@Test(expected = NotFoundException.class)
			public void authenticateIncorrectPassword() throws Exception {
				User user = new User(0, "BadPassword", "abc", null, null, null, null);
				when(mockUserDao.getUserByUsername("BadPassword")).thenReturn(user);
				TestCredentials credentials = new TestCredentials("BadPassword", "def");
				userService.authenticate(credentials);
			}
			
			@Test
			public void authenticateGoodDate() throws Exception{
				TestCredentials credentials = new TestCredentials("good","goodpass");
				User user = new User(0, "good", "goodpass", null, null, null, null);
				when(mockUserDao.getUserByUsername("good")).thenReturn(user);
				String str = userService.authenticate(credentials);
				assertNotNull(str);
			}
}

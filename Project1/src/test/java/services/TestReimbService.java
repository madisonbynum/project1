package services;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import daos.TestReimbDao;
import models.TestTicket;
import models.User;
import util.BadRequestException;


public class TestReimbService {
	
		TestReimbDao mockReimbDao = mock(TestReimbDao.class);
		ReimbService reimbService = new ReimbService(mockReimbDao);

		@Test(expected = BadRequestException.class)
		public void createTicketNoTypeTest() throws Exception {
			User user = new User(1, "madb", "123", "madi", "bynum", "madison@gmail.com", "author");
			TestTicket testTicket = new TestTicket(0, user.getId(), "pending", null, 10.10, "");
			reimbService.createTicket(testTicket);
		}
		
		@Test(expected = BadRequestException.class)
		public void createTicketNoAmountTest() throws Exception {
			User user = new User(1, "madb", "123", "madi", "bynum", "madison@gmail.com", "author");
			TestTicket testTicket = new TestTicket(0, user.getId(), "pending", "food", 0.0, "");
			reimbService.createTicket(testTicket);
		}
		
		@Test(expected = BadRequestException.class)
		public void CreateTicketNoDescriptionIfTypeOther() throws Exception {
			User user = new User(1, "madb", "123", "madi", "bynum", "madison@gmail.com", "author");
			TestTicket testTicket = new TestTicket(0, user.getId(), "pending", "other", 10.10, "");
			reimbService.createTicket(testTicket);
		}
		
		@Test
		public void CreateGoodTicket() throws Exception{
			User user = new User(1, "madb", "123", "madi", "bynum", "madison@gmail.com", "author");
			TestTicket testTicket = new TestTicket(0, user.getId(), "pending", "food", 10.10, "");
			reimbService.createTicket(testTicket);

		}
		
}

package services;

import java.sql.SQLException;
import java.util.List;

import daos.ReimbDao;
import daos.TestReimbDao;
import models.Reimbursement;
import models.TestTicket;
import util.BadRequestException;

public class ReimbService {

	private TestReimbDao testReimbDao;
	ReimbDao reimbDao = new ReimbDao();
	
	Reimbursement reimb = new Reimbursement();

	public ReimbService(TestReimbDao mockReimbDao) {
		this.testReimbDao = mockReimbDao;
	}

	public ReimbService() {
		// TODO Auto-generated constructor stub
	}

	//Junit tests for creating a ticket.
	public TestTicket createTicket(TestTicket ticket) {
		if (ticket.getType() == null || ticket.getAmount() == 0) {
			throw new BadRequestException();
		}
		if (ticket.getType() == "other") {
			if (ticket.getDescription().length() < 5) {
				throw new BadRequestException();
			}
		}
		return ticket;
	}
	
	public Reimbursement createReimb(int userID, String type, double amount) throws SQLException {
		if (type == null || amount == 0) {
			throw new BadRequestException();
		}
		if(type == "other") {
			throw new BadRequestException();
		}
		
		return reimbDao.createReimb(userID, type, amount);
	}
	
	public Reimbursement createReimbIfDesc(int userID, String type, double amount, String description) throws SQLException {
		if (type == null || amount == 0) {
			throw new BadRequestException();
		}
		if (type == "other") {
			if (description.length() < 5) {
				System.out.println("include a description if type other");
				throw new BadRequestException();
			}
		}
		return reimbDao.createReimbIfDesc(userID, type, amount, description);
	}
	
	//Servlet Service for reimbursement
	
	public List<Reimbursement> findAllReimb () throws SQLException {
		
		return reimbDao.findAllReimb();
	}
	
	public List<Reimbursement> findReimbByFnLn (String firstname, String lastname) throws SQLException {
		if (firstname == null || lastname == null) {
			throw new BadRequestException();
		}
		return reimbDao.findReimbByFnLn(firstname, lastname);
	}
	
//	public List<Reimbursement> findReimbByStatus (String status) throws SQLException {
//		return reimbDao.findReimbByStatus(status);
//	}
//	
//	public List<Reimbursement> findReimbByType (String type) throws SQLException {
//		return reimbDao.findReimbByType(type);
//	}
	
	public Reimbursement findReimbByReimbID (int reimbId, int authorID) throws SQLException {
		Reimbursement reimb = reimbDao.findReimbByReimbId(reimbId);
		if(authorID == reimb.getAuthorID()) {
			return reimb;
		}
		else {
			throw new BadRequestException();
		}
	}
	
	public List<Reimbursement> findReimbByAuthorID(int authorID) throws SQLException {
	
		List<Reimbursement> reimbs = reimbDao.findReimbByAuthorID(authorID);
	
		return reimbs;
	}
	
	public Reimbursement updateReimbByR (int reimbID, int resolverID, String status) throws SQLException {
		Reimbursement authorized = reimbDao.findReimbByReimbId(reimbID);
		if(authorized.getAuthorID() == resolverID) {
			throw new BadRequestException();
		}
		else {
			return reimbDao.updateReimbByR(reimbID, resolverID, status);
		}
		
	}
	
	public Reimbursement updateReimbByA (int authorID, int reimbID, String type, double amount) throws SQLException {
		Reimbursement authorized = reimbDao.findReimbByReimbId(reimbID);
		if(authorized.getStatus().equals("approved")|| authorized.getStatus().equals("denied")) {
			throw new BadRequestException();
		}
		if(authorized.getAuthorID() == authorID) {
			return reimbDao.updateReimbByA(reimbID, type, amount);
		}
		else {
			throw new BadRequestException();
		}
	}
	
	public Reimbursement updateReimbByAIfDesc (int authorID, int reimbID, String type, double amount, String description) throws SQLException {
		Reimbursement authorized = reimbDao.findReimbByReimbId(reimbID);
		if(authorized.getStatus().equals("approved")|| authorized.getStatus().equals("denied")) {
			throw new BadRequestException();
		}
		if(authorized.getAuthorID() == authorID) {
			return reimbDao.updateReimbByAIfDesc(reimbID, type, amount, description);
		}
		else {
			throw new BadRequestException();
		}
	}
	

	
	
	
	

}

package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Reimbursement;
import models.User;
import util.BadRequestException;
import util.ConnectionUtil;

public class ReimbDao {

	User user = new User();

	// grab all reimbursements
	private Reimbursement extractReimbursement(ResultSet rs) throws SQLException {
		Reimbursement reimb = new Reimbursement();

		reimb.setId(rs.getInt("id"));
	    reimb.setAuthorID(rs.getInt("author"));
		reimb.setResolverID(rs.getInt("resolver"));
		reimb.setStatus(rs.getString("status"));
		reimb.setType(rs.getString("type"));
		reimb.setAmount(rs.getDouble("amount"));
		reimb.setSubmitted(rs.getString("submitted"));
		reimb.setResolved(rs.getString("resolved"));
		reimb.setDescription(rs.getString("description"));
	
		return reimb;
	}
	
	public String typeQueryNoDesc(String type) {
		if(type.equals("food")) {
			String query = "INSERT INTO reimbursement (author, status, type, amount, resolved) "
					+ "values (?, 'pending', 'food', ?, null) RETURNING *;";
			return query;
		}
		if(type.equals("lodging")) {
			String query = "INSERT INTO reimbursement (author, status, type, amount, resolved) "
					+ "values (?, 'pending', 'lodging', ?, null) RETURNING *;";
			return query;
		}
		if(type.equals("travel")) {
			String query = "INSERT INTO reimbursement (author, status, type, amount, resolved) "
					+ "values (?, 'pending', 'travel', ?, null) RETURNING *;";
			return query;
		}
		else {
			throw new BadRequestException();
		}
	}

	public Reimbursement createReimb(int authorID, String type, double amount) throws SQLException {
		try (Connection conn = ConnectionUtil.getConnection()) {
			System.out.println("Got to dao.createReimb");

			String query = typeQueryNoDesc(type);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, authorID);
			ps.setDouble(2, amount);

			ResultSet rs = ps.executeQuery();
			rs.next();
			Reimbursement reimb = extractReimbursement(rs);

			return reimb;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public String typeQueryIfDesc(String type) {
		if(type.equals("food")) {
			String query = "INSERT INTO reimbursement (author, status, type, amount, resolved, description) "
					+ "values (?, 'pending', 'food', ?, null, ?) RETURNING *;";
			return query;
		}
		if(type.equals("lodging")) {
			String query = "INSERT INTO reimbursement (author, status, type, amount, resolved, description) "
					+ "values (?, 'pending', 'lodging', ?, null, ?) RETURNING *;";
			return query;
		}
		if(type.equals("travel")) {
			String query = "INSERT INTO reimbursement (author, status, type, amount, resolved, description) "
					+ "values (?, 'pending', 'travel', ?, null, ?) RETURNING *;";
			return query;
		}
		if(type.equals("other")) {
			String query = "INSERT INTO reimbursement (author, status, type, amount, resolved, description) "
					+ "values (?, 'pending', 'other', ?, null, ?) RETURNING *;";
			return query;
		}
		else {
			throw new BadRequestException();
		}
	}
	
	public Reimbursement createReimbIfDesc(int userID, String type, double amount, String description) throws SQLException{
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String query = typeQueryIfDesc(type);
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setDouble(2, amount);
			ps.setString(3, description);

			ResultSet rs = ps.executeQuery();
			rs.next();
			
			Reimbursement reimb = extractReimbursement(rs);

			return reimb;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//find by reimb by firstname and lastname (user)
		//select * from reimburse
			//inner join users where firstname = '' and lastname = ''
	public List<Reimbursement> findAllReimb () throws SQLException{
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT * FROM reimbursement;";
			PreparedStatement statement = conn.prepareStatement(query);
			
			ResultSet rs = statement.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<>();
			
			while(rs.next()) {
				Reimbursement reimb = extractReimbursement(rs);
				reimbs.add(reimb);
			}
			
		return reimbs;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		return null;
		}
	}
	
	public List<Reimbursement> findReimbByFnLn (String firstname, String lastname) throws SQLException{
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT * FROM reimbursement INNER JOIN users ON reimbursement.author = users.id WHERE firstname = ? AND lastname = ?;";
			PreparedStatement statement = conn.prepareStatement(query);
		
			statement.setString(1, firstname);
			statement.setString(2, lastname);
			
			ResultSet rs = statement.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<>();
			
			while(rs.next()) {
				Reimbursement reimb = extractReimbursement(rs);
				reimbs.add(reimb);
			}
			
		return reimbs;
		}
		
		catch(SQLException e) {
			e.printStackTrace();
		return null;
		}
	}
	
//	public List<Reimbursement> findReimbByStatus (String status) throws SQLException{
//		try(Connection conn = ConnectionUtil.getConnection()){
//			String query = "SELECT * FROM reimbursement WHERE status = ?";
//			PreparedStatement statement = conn.prepareStatement(query);
//		
//			statement.setString(1, status);
//			
//			ResultSet rs = statement.executeQuery();
//			List<Reimbursement> reimbs = new ArrayList<>();
//			
//			while(rs.next()) {
//				Reimbursement reimb = extractReimbursement(rs);
//				
//				reimbs.add(reimb);
//			}
//			
//		return reimbs;
//		}
//		
//		catch(SQLException e) {
//			e.printStackTrace();
//		return null;
//		}
//	}
	
//	public List<Reimbursement> findReimbByType (String type) throws SQLException{
//		try(Connection conn = ConnectionUtil.getConnection()){
//			String query = "SELECT * FROM reimbursement WHERE type = ?";
//			PreparedStatement statement = conn.prepareStatement(query);
//		
//			statement.setString(1, type);
//			
//			ResultSet rs = statement.executeQuery();
//			List<Reimbursement> reimbs = new ArrayList<>();
//			
//			while(rs.next()) {
//				Reimbursement reimb = extractReimbursement(rs);
//				
//				reimbs.add(reimb);
//			}
//			
//		return reimbs;
//		}
//		
//		catch(SQLException e) {
//			e.printStackTrace();
//		return null;
//		}
//	}
	
	public Reimbursement findReimbByReimbId(int reimbId) throws SQLException{
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT * FROM reimbursement WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, reimbId);
			
			ResultSet rs = statement.executeQuery();
			rs.next();
			Reimbursement reimb = extractReimbursement(rs);
			
			return reimb;
		}
		catch(SQLException e) {
			e.printStackTrace();
		return null;
		}
	}
	
	public List<Reimbursement> findReimbByAuthorID (int authorID) throws SQLException{
		try(Connection conn = ConnectionUtil.getConnection()){
			String query = "SELECT * FROM reimbursement WHERE author = ?";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, authorID);
			
			ResultSet rs = statement.executeQuery();
			List<Reimbursement> reimbs = new ArrayList<>();
			while(rs.next()) {
				Reimbursement reimb = extractReimbursement(rs);
				reimbs.add(reimb);
			}
			
			return reimbs;
		}
		catch(SQLException e) {
			e.printStackTrace();
		return null;
		}
	}
	
	public String updateQueryR(String status) {
		if(status.equals("pending")) {
			String query = "UPDATE reimbursement SET resolver = ?, resolved = current_timestamp, status = 'pending' WHERE id = ? returning *;";
			return query;
		}
		if(status.equals("approved")) {
			String query = "UPDATE reimbursement SET resolver = ?, resolved = current_timestamp, status = 'approved' WHERE id = ? returning *;";
			return query;
		}
		if(status.equals("denied")) {
			String query = "UPDATE reimbursement SET resolver = ?, resolved = current_timestamp, status = 'denied' WHERE id = ? returning *;";
			return query;
		}
		else {
			throw new BadRequestException();
		}
	}
	
	public Reimbursement updateReimbByR (int reimbID, int resolverID, String status) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String query = updateQueryR(status);
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, resolverID);
			statement.setInt(2, reimbID);
			
			ResultSet rs = statement.executeQuery();
			rs.next();
			Reimbursement reimb = extractReimbursement(rs);
			
			return reimb;
		}
		catch(SQLException e) {
			e.printStackTrace();
		return null;
		}
	}
	
	public String updateQueryA(String type) {
		if(type.equals("other")) {
			String query = "UPDATE reimbursement SET type = 'other', amount = ? WHERE id = ? returning *;";
			return query;
		}
		if(type.equals("food")) {
			String query = "UPDATE reimbursement SET type = 'food', amount = ? WHERE id = ? returning *;";
			return query;
		}
		if(type.equals("lodging")) {
			String query = "UPDATE reimbursement SET type = 'lodging', amount = ? WHERE id = ? returning *;";
			return query;
		}
		if(type.equals("travel")) {
			String query = "UPDATE reimbursement SET type = 'travel', amount = ? WHERE id = ? returning *;";
			return query;
		}
		else {
			throw new BadRequestException();
		}
	}
	
	public Reimbursement updateReimbByA (int reimbID, String type, double amount) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String query = updateQueryA(type);
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setDouble(1, amount);
			statement.setInt(2, reimbID);
			
			ResultSet rs = statement.executeQuery();
			rs.next();
			Reimbursement reimb = extractReimbursement(rs);
			
			return reimb;
		}
		catch(SQLException e) {
			e.printStackTrace();
		return null;
		}
	}
	
	public String updateQueryAIfDesc(String type) {
		if(type.equals("other")) {
			String query = "UPDATE reimbursement" + 
					"SET type = 'other', amount = ?, description = ?"+ 
					"WHERE id = ?;";
			return query;
		}
		if(type.equals("food")) {
			String query = "UPDATE reimbursement" + 
					"SET type = 'food', amount = ?, description = ?"+ 
					"WHERE id = ?;";
			return query;
		}
		if(type.equals("lodging")) {
			String query = "UPDATE reimbursement" + 
					"SET type = 'lodging', amount = ?, description = ?"+ 
					"WHERE id = ?;";
			return query;
		}
		if(type.equals("travel")) {
			String query = "UPDATE reimbursement" + 
					"SET type = 'travel', amount = ?, description = ?"+ 
					"WHERE id = ?;";
			return query;
		}
		else {
			throw new BadRequestException();
		}
	}
	
	
	public Reimbursement updateReimbByAIfDesc (int reimbID, String type, double amount, String description) {
		try(Connection conn = ConnectionUtil.getConnection()){
			
			String query = updateQueryAIfDesc(type);
			
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setDouble(1, amount);
			statement.setString(2, description);
			statement.setInt(3, reimbID);
			
			ResultSet rs = statement.executeQuery();
			rs.next();
			Reimbursement reimb = extractReimbursement(rs);
			
			return reimb;
		}
		catch(SQLException e) {
			e.printStackTrace();
		return null;
		}
	}
	
	
	
	
	
	

	

	// update from pending to approved or denied

}

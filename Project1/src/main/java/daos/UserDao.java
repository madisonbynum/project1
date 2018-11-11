package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Roles;
import models.User;
import util.ConnectionUtil;

public class UserDao {

	public User extractUser(ResultSet rs) throws SQLException {

		User user = new User();

		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstname(rs.getString("firstname"));
		user.setLastname(rs.getString("lastname"));
		user.setEmail(rs.getString("email"));
		user.setRoles(rs.getString("roles"));
		

		return user;
	}

	public User getUserByUnPw(String username, String password) throws SQLException {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String query = "SELECT * FROM users WHERE username = ? AND \"password\" = ?;";
			PreparedStatement statement = conn.prepareStatement(query);

			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {

				User user = extractUser(rs);
				return user;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public User getUserById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String query = "SELECT * FROM users WHERE id = ?;";
			PreparedStatement statement = conn.prepareStatement(query);

			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();
			User user = extractUser(rs);

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<User> getUserByFullName(String firstname, String lastname) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String query = "SELECT * FROM users WHERE firstname = ? AND lastname = ?;";
			PreparedStatement statement = conn.prepareStatement(query);

			statement.setString(1, firstname);
			statement.setString(2, lastname);

			ResultSet rs = statement.executeQuery();

			List<User> users = new ArrayList<>();

			while (rs.next()) {
				User user = extractUser(rs);
				users.add(user);
			}

			return users;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

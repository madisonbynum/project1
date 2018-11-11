package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.User;
import services.UserService;

public class LoginController {
	UserService userService = new UserService();

	
	public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
	
	}
	
	public User post(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
	
		ObjectMapper mapper = new ObjectMapper();
		User userLogging = mapper.readValue(request.getReader(), User.class);
		
		String username = userLogging.getUsername();
		String password = userLogging.getPassword();
		
		HttpSession session = request.getSession();
		
		User user = userService.getUserByUnPw(username, password);
		
		String role = user.getRoles();
		
		session.setAttribute("userID", user.getId());
		
		if(role.equals("resolver")) {
		session.setAttribute("resolver", role);
		}
		
		mapper.writeValue(response.getWriter(), user);
		System.out.println("got here to login Controller");
		
		return user;
		
	}
	
	
	}

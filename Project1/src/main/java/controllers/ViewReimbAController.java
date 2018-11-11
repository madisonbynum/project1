package controllers;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Reimbursement;
import services.ReimbService;

public class ViewReimbAController {
	
	ReimbService reimbService = new ReimbService();

	public void get(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		System.out.println("get for viewreimba request recieved");
		String uri = request.getRequestURI();
		String[] parts = uri.substring("/Project1/viewreimba/".length()).split("/");
		
		if(parts[0].length() == 0) {
			System.out.println("Error in if statement of viewReimbA.get");
			response.sendError(400);
		}
		else {
			String reimb = parts[0];
			int reimbId = 0;
			
			try {
				HttpSession session = request.getSession();
				int authorID = (Integer) session.getAttribute("userID");
				
				reimbId = Integer.parseInt(reimb);
				Reimbursement reimbInstance = reimbService.findReimbByReimbID(reimbId, authorID);
				if(reimbInstance == null) {
					response.sendError(404);
				}
				else {
					ObjectMapper om = new ObjectMapper();
					om.writeValue(response.getWriter(), reimbInstance);
				}
				
			}
			catch (NumberFormatException e) {
				response.sendError(400);
				System.out.println("Bad request");
				return;
			}
		}
	}
	
	public List<Reimbursement> post(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
			System.out.println("post request for viewReimbA request recieved");
			
			HttpSession session = request.getSession();
			int authorID = (Integer) session.getAttribute("userID");
			
			List<Reimbursement> reimbs = reimbService.findReimbByAuthorID(authorID);
		
			return reimbs;
			
	}

}

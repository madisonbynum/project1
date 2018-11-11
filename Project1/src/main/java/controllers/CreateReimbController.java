package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Reimbursement;
import services.ReimbService;

public class CreateReimbController {
	ReimbService reimbService = new ReimbService();

	public Reimbursement post(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, SQLException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userID") == null) {
			response.sendError(400);
		}
		
		System.out.println("inside post");
		int authorID = (Integer) session.getAttribute("userID");
		
		ObjectMapper om = new ObjectMapper();
		Reimbursement newReimb = om.readValue(request.getReader(), Reimbursement.class);
		
		String description =  newReimb.getDescription();
		double amount = newReimb.getAmount();
		String type = newReimb.getType();
		
		System.out.println(authorID);
		System.out.println(description);
		System.out.println(amount);
		System.out.println(type);

		
		if(description == null && !(type == "other")) {
			System.out.println("Got to inside if statement in createreimb");
			Reimbursement reimb = reimbService.createReimb(authorID, type, amount);
			return reimb;
		}
		else {
			System.out.println("Got to inside of else statement in createreimb");
			Reimbursement reimb = reimbService.createReimbIfDesc(authorID, type, amount, description);
			return reimb;
		}
	}
}

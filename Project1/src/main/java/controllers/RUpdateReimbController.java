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
import util.BadRequestException;

public class RUpdateReimbController {
	
	ReimbService reimbService = new ReimbService();

	public Reimbursement post(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, SQLException {
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("resolver");
		
		if (role.equals("resolver")) {
		
		int resolverID = (Integer) session.getAttribute("userID");
		
		ObjectMapper om = new ObjectMapper();
		Reimbursement updateReimb = om.readValue(request.getReader(), Reimbursement.class);
		
		String status = updateReimb.getStatus();
		int reimbID = updateReimb.getId();

		Reimbursement reimb = reimbService.updateReimbByR(reimbID, resolverID, status);
		return reimb;
	}
	
	 else {
		throw new BadRequestException();
	}

}
}
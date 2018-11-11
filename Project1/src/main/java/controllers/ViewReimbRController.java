package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Reimbursement;
import models.User;
import services.ReimbService;
import util.BadRequestException;

public class ViewReimbRController {

	ReimbService reimbService = new ReimbService();

	public List<Reimbursement> get(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		HttpSession session = request.getSession();

		String role = (String) session.getAttribute("resolver");

		if (role.equals("resolver")) {

			List<Reimbursement> reimbs = reimbService.findAllReimb();

			return reimbs;
		} else {
			throw new BadRequestException();
		}

	}

	public List<Reimbursement> post(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		HttpSession session = request.getSession();

		String role = (String) session.getAttribute("resolver");
		
		if (role.equals("resolver")) {
			ObjectMapper mapper = new ObjectMapper();

			User findUser = mapper.readValue(request.getReader(), User.class);

			String firstname = findUser.getFirstname();
			String lastname = findUser.getLastname();
			int authorID = findUser.getId();

			if (firstname == null && lastname == null) {
				List<Reimbursement> reimbs = reimbService.findReimbByAuthorID(authorID);
				return reimbs;
			} else {
				List<Reimbursement> reimbs = reimbService.findReimbByFnLn(firstname, lastname);
				return reimbs;
			}
		}
	 else {
		throw new BadRequestException();
	}

}
	
}

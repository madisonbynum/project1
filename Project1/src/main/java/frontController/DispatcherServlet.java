package frontController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.AUpdateReimbController;
import controllers.CreateReimbController;
import controllers.LoginController;
import controllers.RUpdateReimbController;
import controllers.ViewReimbAController;
import controllers.ViewReimbRController;
import models.Reimbursement;
import models.Route;
import models.User;

public class DispatcherServlet extends DefaultServlet {

	LoginController loginController = new LoginController();
	ViewReimbAController viewReimbAController = new ViewReimbAController();
	CreateReimbController createReimbController = new CreateReimbController();
	ViewReimbRController viewReimbRController = new ViewReimbRController();
	RUpdateReimbController rUpdateReimbController = new RUpdateReimbController();
	AUpdateReimbController aUpdateReimbController = new AUpdateReimbController();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		//new headers to see if credentials and cookies will save to angular
//		response.addHeader("Access-Control-Allow-Credentials", "true");
//		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");	 
		super.service(request, response);
	}

	@Override
	public void init() {
		System.out.println("default servlet initializing");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Route route = getRoute(request);
		System.out.println("Got to doGet");

		switch (route) {

		case VIEWREIMBA:
			try {
				viewReimbAController.get(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case VIEWREIMBR:
			try {
				List<Reimbursement> reimbs = viewReimbRController.get(request, response);

				ObjectMapper om = new ObjectMapper();

				for (Reimbursement reimb : reimbs) {

					om.writeValue(response.getWriter(), reimbs);
					System.out.println(reimb);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		default:
			response.setStatus(404);

		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Route route = getRoute(request);
		System.out.println("Got to doPost case login");

		switch (route) {

		case LOGIN:
			System.out.println("Got to case Login");
			try {
				User user = loginController.post(request, response);
				ObjectMapper om = new ObjectMapper();

				om.writeValue(response.getWriter(), user);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case VIEWREIMBA:

			System.out.println("Got to doPost case viewreimba");

			try {
				List<Reimbursement> reimbs = viewReimbAController.post(request, response);
				ObjectMapper om = new ObjectMapper();

				for (Reimbursement reimb : reimbs) {

					om.writeValue(response.getWriter(), reimbs);
					System.out.println(reimb);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case VIEWREIMBR:

			try {
				List<Reimbursement> reimbs = viewReimbRController.post(request, response);
				ObjectMapper om = new ObjectMapper();

				for (Reimbursement reimb : reimbs) {

					om.writeValue(response.getWriter(), reimbs);
					System.out.println(reimb);

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case CREATEREIMB:
			System.out.println("Got to CreateReimb");
			try {
				Reimbursement reimb = createReimbController.post(request, response);
				ObjectMapper om = new ObjectMapper();
				om.writeValue(response.getWriter(), reimb);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case UPDATEREIMBR: {
			System.out.println("Got to UpdateReimbR");
			
			try {
				Reimbursement reimb = rUpdateReimbController.post(request, response);
				ObjectMapper om = new ObjectMapper();
				om.writeValue(response.getWriter(), reimb);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
		case UPDATEREIMBA:
			System.out.println("Got to UpdateReimbA");

			try {
				Reimbursement reimb = aUpdateReimbController.post(request, response);
				ObjectMapper om = new ObjectMapper();
				om.writeValue(response.getWriter(), reimb);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case NOT_FOUND:

		default:
			response.setStatus(404);
		}
	}

	private Route getRoute(HttpServletRequest request) {
		String suffix = request.getRequestURI().substring("/Project1/".length());
		String roleString = suffix.split("/")[0];
		try {
			return Route.valueOf(roleString.toUpperCase());
		} catch (IllegalArgumentException e) {
			return Route.NOT_FOUND;
		}
	}

//	void writeStaticFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		RequestDispatcher rd = getServletContext().getNamedDispatcher("default");
//		HttpServletRequest wrapped = new HttpServletRequestWrapper(request) {
//			public String getServletPath() { return ""; }
//		};
//		rd.forward(wrapped, response);
//	}
}

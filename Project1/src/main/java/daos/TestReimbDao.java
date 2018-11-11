package daos;

import models.Reimbursement;
import models.User;

public interface TestReimbDao {
	
						//Tests
	//=============================================
	
	//find tickets - dont actually need to test. 
	//User is authenticated and tickets are authenticated
	//Only need a good query to find the right tickets to send to servlet
	Reimbursement getTicketByUserID (User id);
	
	//create tickets
	Reimbursement createTicketByUserID (User id);
	
	//update tickets
	Reimbursement updateTicketByTicketID(Reimbursement id);
}

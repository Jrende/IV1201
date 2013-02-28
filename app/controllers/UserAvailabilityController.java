package controllers;

import java.util.Date;

import models.Availability;
import models.User;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import play.data.*;
import views.html.applicantView;

/**
 * Manage a database of Availabilitys
 */
public class UserAvailabilityController extends Controller {
	
	/**
	 * Class representing Availability as a Form
	 *
	 */
	public static class AvailabilityForm {
		public Date to_date;
		public Date from_date;
		
		/**
		 * Validate that both to_date and end_date is specified.
		 * 
		 * @return - null on success, else error message.
		 */
		public String validate() {
			if(to_date == null || from_date == null) {
				return "Please specify both dates!";
			}	
			return null;
		}
	}
	
	/**
	 * 
	 * @return - Redirects back to form on error, otherwise saves new Availability and redirects to index
	 */
	@Security.Authenticated(Secured.class)
	public static Result addAvailability() {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		Form<AvailabilityForm> compForm = form(AvailabilityForm.class).bindFromRequest();
		
		if (compForm.hasErrors()) {
			return badRequest(applicantView.render(user, form(UserController.CompetenceProfileForm.class), form(AvailabilityForm.class)));
		} 
		else {
			Availability a = new Availability();
			a.person_id = user;
			a.from_date = compForm.get().from_date;
			a.to_date = compForm.get().to_date;
			user.availabilityList.add(a);
			user.update();
			return redirect(routes.Index.index());
		}
	}
	
	  /**
     * Handle availability deletion
     */
	@Security.Authenticated(Secured.class)
    public static Result deleteAvailability(Long id) {
        Availability.findById(id).delete();
        return redirect(routes.Index.index());
    }

}
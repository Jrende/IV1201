package controllers;

import java.util.Date;

import models.Availability;
import models.User;

import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import play.data.*;
import views.html.error;

/**
 * Manage a database of Availabilities
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
			if((to_date == null) || (from_date == null)) {
				return Availability.validate(to_date, from_date);
			}
			return null;
		}
	}
	
	/**
	 * Add new availability to logged in user. 
	 * 
	 * @return - Redirects to errorview on error, otherwise saves new Availability and redirects to index
	 */
	@Security.Authenticated(Secured.class)
	public static Result addAvailability() {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		Form<AvailabilityForm> compForm = form(AvailabilityForm.class).bindFromRequest();
		if (compForm.hasErrors()) {
			return badRequest(error.render(Messages.get("error.dateFormat")));
		}
		else {
			Availability a = new Availability();
			a.person = user;
			a.from_date = compForm.get().from_date;
			a.to_date = compForm.get().to_date;
			user.availabilityList.add(a);
			user.update();
			
		}
		return redirect(routes.Index.index());
	}
	
	 /**
	  * Deletes a specified availaibility for the logged in user. 
	  * 
	  * @param id the id of the availability to delete
	  * @return
	  */
	@Security.Authenticated(Secured.class)
    public static Result deleteAvailability(Long id) {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		Availability availability = Availability.findById(id);
		if(availability == null) {
			return badRequest(error.render(Messages.get("error.availabilityNotFound")));
		}
		if(!availability.person.person_id.equals(user.person_id)) {
			return badRequest(error.render(Messages.get("error.availabilityNotYours")));
		}
		availability.delete();
        return redirect(routes.Index.index());
    }

}

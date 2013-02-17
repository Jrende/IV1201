
package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

/**
 * Return HTTP Server index.
 */
@Security.Authenticated(Secured.class)
public class Index extends Controller {
	//Todo: Move the adding of competences to UserController, where it belongs.
	public static class CompetenceProfileForm {
		public Competence competence;
		public float yearsOfExperience;
		/**
		 * Validate whether the competence exists in database.
		 * 
		 * @return - null on success, else error message.
		 */
		public String validate() {
			return null;
		}
	}
	/**
	 * Returns index page for HTTP.
	 * 
	 * @return - Rendered index page as response.
	 */
	public static Result index() {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		
		return ok(index.render(user, Competence.getAll(), form(CompetenceProfileForm.class)));
	}
	public static Result addCompetence() {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		return ok(index.render(user, Competence.getAll(), form(CompetenceProfileForm.class)));
	}
}


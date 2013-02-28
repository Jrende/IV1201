package controllers;

import play.mvc.*;
import play.i18n.*;

import models.*;
import views.html.*;

/**
 * Manage the activities of a User that is an recruiter
 */
@Security.Authenticated(Secured.class)
public class RecruiterController extends Controller {
	
	/**
	 * Show detailed view of user if allowed, otherwise error will be displayed
	 * 
	 * @param id
	 * @return detailed view of choosen user
	 */
	public static Result getDetailedUserView(int id) {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		if(user.role != Role.Recruiter) {
			return badRequest(error.render(Messages.get("error.notAuthorized")));
		}

		User chosenUser = User.findById(id);
		if(chosenUser == null) {
			return badRequest(error.render(Messages.get("error.userNotFound")));
		}

		return ok(userView.render(chosenUser));
	}

	/**
	 * Hire choosen user if allowed, otherwise display error
	 * 
	 * @param id
	 * @return detailed view of hired
	 */
	public static Result hireApplicant(int id) {
		String username = Http.Context.current().request().username();
		User currentUser = User.findByUsername(username);
		if(currentUser.role != Role.Recruiter) {
			return badRequest(error.render(Messages.get("error.notAuthorized")));
		}
		User chosenUser = User.findById(id);
		if(chosenUser == null) {
			return badRequest(error.render(Messages.get("error.userNotFound")));
		}
		chosenUser.isHired = true;
		chosenUser.update();
		return redirect(routes.RecruiterController.getDetailedUserView(id));
	}

	/**
	 * Deny chosen user, if allowed, otherwise display error.
	 * 
	 * @param id
	 * @return detailed view of chosen user
	 */
	public static Result denyApplicant(int id) {
		String username = Http.Context.current().request().username();
		User currentUser = User.findByUsername(username);
		if(currentUser.role != Role.Recruiter) {
			return badRequest(error.render(Messages.get("error.notAuthorized")));
		}
		User chosenUser = User.findById(id);
		if(chosenUser == null) {
			return badRequest(error.render(Messages.get("error.userNotFound")));
		}
		chosenUser.isHired = false;
		chosenUser.update();
		return redirect(routes.RecruiterController.getDetailedUserView(id));
	}
	
	/**
	 * Print pdf with applicant information, if allowed, otherwise display error. 
	 * 
	 * @param id
	 * @return
	 */
	public static Result printApplicantPDF(int id) {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		if(user.role != Role.Recruiter) {
			return badRequest(error.render(Messages.get("error.notAuthorized")));
		}

		User chosenUser = User.findById(id);
		if(chosenUser == null) {
			return badRequest(error.render(Messages.get("error.userNotFound")));
		}

		return RenderPDF.renderPDFfile(userView.render(chosenUser), new String(User.findById(id).name) + ".pdf");
	}
}

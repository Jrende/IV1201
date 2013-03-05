package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import play.mvc.*;
import play.i18n.*;

import models.*;
import util.RenderPDF;
import views.html.*;

/**
 * Manage the activities of a User that is a recruiter
 */
@Security.Authenticated(Secured.class)
public class RecruiterController extends Controller {
	
	/**
	 * Show detailed view of user if allowed, otherwise error will be displayed
	 * 
	 * @param id the id of the User to display detailed view of. 
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
	 * @param id the id of the User being hired
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
	 * @param id the id of the user to deny. 
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
	 * @param id id of applicant to print pdf-version of
	 * @return on success returns a view of the generated pdf
	 */
	public static Result printApplicantPDF(int id) {
		String username = Http.Context.current().request().username();
		User admin = User.findByUsername(username);
		
		if(admin.role != Role.Recruiter) {
			return badRequest(error.render(Messages.get("error.notAuthorized")));
		}

		User user = User.findById(id);
		if(user == null) {
			return badRequest(error.render(Messages.get("error.userNotFound")));
		}

		File pdfFile = RenderPDF.renderPDFfile(userView.render(user), user.name+"."+user.surname);
		if (pdfFile == null)
			return internalServerError();
		
		try {
			response().setContentType("application/pdf ");
			return ok(new FileInputStream(pdfFile));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return internalServerError();
		}
	}
}

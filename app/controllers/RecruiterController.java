package controllers;

import play.mvc.*;
import play.i18n.*;

import models.*;
import views.html.*;


@Security.Authenticated(Secured.class)
public class RecruiterController extends Controller {
	
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
		System.out.println("Hire user");
		chosenUser.isHired = true;
		chosenUser.update();
		return redirect(routes.RecruiterController.getDetailedUserView(id));
	}

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
		System.out.println("Deny user");
		chosenUser.isHired = false;
		chosenUser.update();
		return redirect(routes.RecruiterController.getDetailedUserView(id));
	}
	
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

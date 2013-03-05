package controllers;

import java.text.ParseException;
import java.util.Locale;

import models.Competence;
import models.CompetenceProfile;
import models.User;
import play.data.Form;
import play.data.format.Formatters;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.applicantView;
import views.html.error;

public class UserCompetenceController extends Controller {
	
	public static class CompetenceProfileForm {
		public Competence competence;
		public float yearsOfExperience;
		
		/**
		 * Validate whether the competence exists in database.
		 * 
		 * @return - null on success, else error message.
		 */
		public String validate() {
			if(competence == null) {
				return "Please select a competence!";
			}	
			return null;
		}
	}
	
	/**
	 * Add a new Competence to a user
	 * 
	 * @return index-page of logged in user
	 */
	@Security.Authenticated(Secured.class)
	public static Result addCompetence() {

		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);

		//Adds a custom formatter to convert from String to Competence
		Formatters.register(Competence.class, new Formatters.SimpleFormatter<Competence>() {
			@Override
			public Competence parse(String input, Locale arg1) throws ParseException {
				Competence comp = Competence.findById(Integer.parseInt(input));
				return comp;
			}
			
			@Override
			public String print(Competence comp, Locale arg1) {
				return Long.toString(comp.competence_id);
			}
		});

		Form<CompetenceProfileForm> compForm = form(CompetenceProfileForm.class).bindFromRequest();

		if (compForm.hasErrors()) {
			return badRequest(applicantView.render(user, form(CompetenceProfileForm.class), form(UserAvailabilityController.AvailabilityForm.class)));
		} else {
			CompetenceProfile p = new CompetenceProfile();
			p.person = user;
			p.competence = compForm.get().competence;
			p.years_of_experience = compForm.get().yearsOfExperience;
			user.competenceProfileList.add(p);
			user.update();
			return redirect(routes.Index.index());
		}

	}
	
	/**
	 * Remove competence with specified id from logged in user.
	 * 
	 * @param id the id of the competence to remove
	 * @return index view of logged in user
	 */
	@Security.Authenticated(Secured.class)
	public static Result removeCompetenceProfile(int id) {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		CompetenceProfile comp = CompetenceProfile.findById(id);
		if(comp == null) {
			return badRequest(error.render(Messages.get("error.competenceProfileNotFound")));
		}
		if(!comp.person.person_id.equals(user.person_id)) {
			return badRequest(error.render(Messages.get("error.competenceProfileNotYours")));
		}
		comp.delete();
		return redirect(routes.Index.index());
	}

}

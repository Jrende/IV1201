package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

	//-- Authentication

		public static class Login {

			public String username;
			public String password;

			public String validate() {
				if(User.authenticate(username, password) == null) {
					return "Invalid user or password";
				}
				return null;
			}

		}

	/**
	 * Login page.
	 */
	public static Result login() {
		return ok(
				login.render(form(Login.class))
				);
	}

	/**
	 * Handle login form submission.
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if(loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session("name", loginForm.get().username);
			return redirect(
					routes.Application.index()
					);
		}
	}

	/**
	 * Logout and clean the session.
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(
				routes.Application.login()
				);
	}

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result register() {
		return ok(register.render(User.getAll(), form(User.class)));
	}

	public static Result newUser() {
		System.out.println("newUser()");
		Form<User> userForm = form(User.class).bindFromRequest();
		if(userForm.hasErrors()) {
			System.out.println("form has errors");
			return badRequest(register.render(User.getAll(), userForm));
		} else {
			System.out.println("Ny användare: " + userForm.get());
			userForm.get().save();
			return redirect(routes.Application.index());
		}
	}


}


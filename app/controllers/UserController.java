package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

<<<<<<< HEAD:app/controllers/Application.java
public class Application extends Controller {

	// -- Authentication

	public static class Login {
=======
public class UserController extends Controller {

	//-- Defines a login form
		public static class Login {
>>>>>>> 96a3a12aaace490637a72f2fd05b5dc67e8c2332:app/controllers/UserController.java

		public String username;
		public String password;

		public String validate() {
			if (User.authenticate(username, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}

	}

	/**
	 * Renders the login view
	 */
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	/**
	 * Authenticates user and creates a session cookie.
	 */
	public static Result authenticate() {
		Form<Login> loginForm = form(Login.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm));
		} else {
			session("name", loginForm.get().username);
			return redirect(routes.Index.index());
		}
	}

	/**
	 * Logs out and clears cookie
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
<<<<<<< HEAD:app/controllers/Application.java
		return redirect(routes.Application.login());
=======
		return redirect(
				routes.UserController.login()
				);
>>>>>>> 96a3a12aaace490637a72f2fd05b5dc67e8c2332:app/controllers/UserController.java
	}

	public static Result register() {
		return ok(register.render(form(User.class)));
	}

	/**
	 *	Creates and persists a new user.
	 */
	public static Result newUser() {
		Form<User> userForm = form(User.class).bindFromRequest();
		if (userForm.hasErrors()) {
			System.out.println("Userform has errors");
			return badRequest(register.render(userForm));
		} else {
			System.out.println("Saving user");
			userForm.get().save();
			return redirect(routes.Index.index());
		}
	}
	
	public static Result usernameAvailable(String username) {
		if (User.usernameAvailable(username))
			return ok("true");
		
		return ok("false");
	}
	
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(
				Routes.javascriptRouter("jsRoutes",
						// Routes
						controllers.routes.javascript.Application.usernameAvailable())
		);
	}

}

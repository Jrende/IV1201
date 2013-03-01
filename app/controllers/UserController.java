package controllers;


import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

/**
 * Manages a database of users
 */
public class UserController extends Controller {

	/**
	 * Authentication
	 */
	public static class Login {
		public String username;
		public String password;

		/**
		 * Validate user name  and password.
		 * 
		 * @return - null on success, else error message.
		 */
		public String validate() {
			if (User.authenticate(username, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}
	}

	/**
	 * 
	 * @return login-view
	 */
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	/**
	 * Authenticates user and creates a session cookie.
	 *
	 * @return index-view on success, login-view on error
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
	 * Logout user, clear session cookie
	 * 
	 * @return login-view
	 */
	public static Result logout() {
		session().clear();
		return redirect(routes.UserController.login());
	}

	/**
	 * redirects to register view
	 * 
	 * @return register view
	 */
	public static Result register() {
		return ok(register.render(form(User.class)));
	}

	/**
	 * 
	 * 
	 * @return login-view on success, redirects back to register-view on error
	 */
	public static Result newUser() {
		Form<User> userForm = form(User.class).bindFromRequest();
		if(userForm.hasErrors()) {
			return badRequest(register.render(userForm));
		} else {
			userForm.get().save();
			return redirect(routes.Index.index());
		}
	}
	
	/**
	 * Checks if username is available
	 * 
	 * @param username
	 * @return true if available
	 */
	public static Result usernameAvailable(String username) {
		if (User.usernameAvailable(username)) {
			return ok("true");
		}
		return ok("false");
	}
	
	/**
	 * Setup routes for javascript calls.
	 * 
	 * @return jsroutes
	 */
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(
				Routes.javascriptRouter("jsRoutes",
						// Routes
						controllers.routes.javascript.UserController.usernameAvailable())
		);
	}


	

	
}

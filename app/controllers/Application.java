package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;

public class Application extends Controller {

	// -- Authentication

	public static class Login {

		public String username;
		public String password;

		public String validate() {
			if (User.authenticate(username, password) == null) {
				return "Invalid user or password";
			}
			return null;
		}

	}
		
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(
				Routes.javascriptRouter("jsRoutes",
						// Routes
						controllers.routes.javascript.UserController.usernameAvailable())
		);
	}

}

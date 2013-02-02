
package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import models.*;
import views.html.*;


@Security.Authenticated(Secured.class)
public class Index extends Controller {
	public static Result index() {
		String username = Http.Context.current().request().username();
		User user = User.findByUsername(username);
		return ok(
				index.render(user)
				);
	}
}


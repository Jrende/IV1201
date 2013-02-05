package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

/**
 * See: {@link http://www.playframework.org/documentation/api/2.0.4/java/play/mvc/Security.Authenticator.html}
 *
 */
public class Secured extends Security.Authenticator {
	/**
	 * See: {@link http://www.playframework.org/documentation/api/2.0.4/java/play/mvc/Security.Authenticator.html#getUsername%28play.mvc.Http.Context%29}
	 *
	 */
	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("name");
	}

	/**
	 * See {@link http://www.playframework.org/documentation/api/2.0.4/java/play/mvc/Security.Authenticator.html#onUnauthorized%28play.mvc.Http.Context%29}
	 */
	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.UserController.login());
	}   
}


package util;

import models.User;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Before;
import play.mvc.Controller;

public class ControllerUtil extends Controller {


	public static User getLoginUser(){

		User user = User.findById(session.get("id"));
		if ( user == null) {
			return null;
		} else {
			return user;
		}


	}



}

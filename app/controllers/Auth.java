package controllers;

import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.Before;
import play.mvc.Controller;

public class Auth extends Controller {


	@Before(unless = { "login", "mixi", "logout" })
	static void begin() {
		if (!isLoggedIn()) {
			login();
		}
	}

	private static boolean isLoggedIn() {
		return session.contains("username");
	}

	public static void login() {
		render();
	}

	public static void mixi() {
		authenticate("https://mixi.jp");
	}

	private static void authenticate(String url) {
		// 認証完了のレスポンスでなければ OpenID の検証を要求する
		if (!OpenID.isAuthenticationResponse()) {
			OpenID.id(url).required("nickname").verify(); //nicknameも要求する
			return;
		}

		// 認証処理の結果を評価
		UserInfo user = OpenID.getVerifiedID();
		if (user == null) {
			flash.put("error", "認証に失敗しました");
			login();
		} else {

			// 認証完了
			String nickname = user.extensions.get("nickname");

			session.put("username", nickname);
			render("@Application.index");
		}
	}

	public static void logout() {
		session.clear();
		login();
	}

}

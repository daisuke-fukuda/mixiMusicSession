package controllers;

import models.User;
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
		return session.contains("id");
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
		UserInfo userInfo = OpenID.getVerifiedID();
		if (userInfo == null) {
			flash.put("error", "認証に失敗しました");
			login();
		} else {

			// 認証完了
			String nickname = userInfo.extensions.get("nickname");
			String id = userInfo.id;

			session.put("nickname", nickname);
			session.put("id", id);

			// ユーザー情報をアプリで保持
			User user = User.findById(id);
			if ( user == null) {
				user = new User();
				user.id = id;
			}
			user.name = nickname;
			user.save();


			Application.index();
		}
	}

	public static void logout() {
		session.clear();
		login();
	}

}

package util;

import java.util.Iterator;
import java.util.List;

import models.User;
import play.mvc.Controller;

/**
 * @author moyashi
 *
 */
/**
 * @author moyashi
 *
 */
public class Util extends Controller {



	/**
	 *
	 * ログイン済のユーザー情報を取得する
	 * @return ログイン済のユーザー情報
	 *
	 */
	public static User getLoginUser() {

		User user = User.findById(session.get("id"));
		if (user == null) {
			return null;
		} else {
			return user;
		}

	}



	/**
	 * Listを整形して出力する
	 * イメージ：aaa, hoge, cccc
	 *
	 * @param 整形出力したいList
	 * @return 整形済のString
	 */
	public static String getFormattedString(List list) {

		Iterator<Object> i = list.iterator();
		if (!i.hasNext())
			return "";
		StringBuilder sb = new StringBuilder();
		for (;;) {
			Object e = i.next();
			sb.append(e);
			if (!i.hasNext())
				return sb.toString();
			sb.append(", ");
		}

	}


	/**
	 *
	 * 渡された文字列を改行区切りで結合する
	 *
	 * @param 結合したい文字列
	 * @return String
	 */
	public static String appendLn(String... strings){

		int length = 0;
		if (strings == null || (length = strings.length) == 0){
			return "";
		} else {
			final String BR = System.getProperty("line.separator");
			StringBuilder sb = new StringBuilder();
			String s;
			for (int i = 0 ; i < length - 1 ; i++){
				s = strings[i];
				sb.append(s + BR);
			}
			sb.append(strings[length - 1]);
			return sb.toString();
		}

	}



}

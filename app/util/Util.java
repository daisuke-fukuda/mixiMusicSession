package util;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import models.User;
import play.mvc.Controller;

/**
 *
 * @author moyashidaisuke
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


		String id = session.get("id");
		if ( StringUtils.isNotBlank(id)){
			User user = User.findById(id);
			if (user != null) {
				return user;
			}
		}

		return null;

	}



	/**
	 * Listを整形して出力する
	 * イメージ：aaa, hoge, cccc
	 *
	 * @param 整形出力したいList
	 * @return 整形済のString
	 */
	public static String list2StringWithoutBrackets(List list) {

		if (list != null){
			String s = list.toString();
			//最初と最後の括弧を削除
			return s.substring(1, s.length() - 1);
		}
		return "";
	}


	/**
	 *
	 * 渡された文字列を改行区切りで結合する
	 *
	 * @param 結合したい文字列
	 * @return String
	 */
	public static String appendLn(String... strings) {

		if (strings != null) {
			int length = strings.length;
			if (length != 0) {
				final String BR = System.getProperty("line.separator");
				StringBuilder sb = new StringBuilder();
				String s;
				for (int i = 0; i < length - 1; i++) {
					s = strings[i];
					sb.append(s + BR);
				}
				sb.append(strings[length - 1]);
				return sb.toString();
			}
		}
		return "";
	}


}

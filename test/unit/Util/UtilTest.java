package unit.Util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;

import models.User;
import net.sf.cglib.transform.impl.AddDelegateTransformer;

import org.junit.Test;

import play.mvc.Controller;
import play.test.Fixtures;
import play.test.UnitTest;
import util.Util;

public class UtilTest extends UnitTest {


	@org.junit.Before
	public void setup() {
		Fixtures.deleteDatabase();
	}




	@Test
	public void testGetLoguinUser_sessionのidが無い場合Nullが返ってくる(){

		// セッションの削除
		new SessionController().deleteUserSession();

		// 実行
		User user = Util.getLoginUser();

		// テスト
		assertThat(user, nullValue());
	}



	@Test
	public void testGetLoguinUser_sessionのidがあるけどDBに無い場合Nullが返ってくる(){

		// セッションの追加
		new SessionController().addUserSession("notExistsId");

		// 実行
		User user = Util.getLoginUser();

		// テスト
		assertThat(user, nullValue());
	}



	@Test
	public void testGetLoguinUser_sessionのidがあってDBにある場合Userが返ってくる(){

		// DBにユーザー情報追加
		User user = new User();
		user.id = "testId";
		user.name = "testName";
		user.save();

		// セッションの追加
		new SessionController().addUserSession("testId");

		// 実行
		User assertUser = Util.getLoginUser();

		// テスト
		assertThat(assertUser, notNullValue());
		assertThat(assertUser.name, equalTo("testName"));

	}




	@Test
	public void testList2StringWithoutBrackets_NULLの場合空文字が返ってくる(){

		String s = Util.list2StringWithoutBrackets(null);

		assertThat(s, equalTo(""));
	}

	@Test
	public void testList2StringWithoutBrackets_空のListの場合空文字が返ってくる(){

		String s = Util.list2StringWithoutBrackets(new ArrayList());

		assertThat(s, equalTo(""));
	}


	@Test
	public void testList2StringWithoutBrackets_Listを渡すとカンマ区切り文字列が返ってくる(){

		String s = Util.list2StringWithoutBrackets(new ArrayList(){{add("hoge");add("fuga");add("a");}});

		assertThat(s, equalTo("hoge, fuga, a"));
	}

	@Test
	public void testList2StringWithoutBrackets_要素数1のListを渡すと要素1の文字列が返ってくる(){

		String s = Util.list2StringWithoutBrackets(new ArrayList(){{add("hoge");}});

		assertThat(s, equalTo("hoge"));
	}



	@Test
	public void testAppendLn_NULLを渡すと空文字が返ってくる(){
		String s = Util.appendLn(null);

		assertThat(s, equalTo(""));
	}

	@Test
	public void testAppendLn_空文字を渡すと空文字が返ってくる(){

		String s = Util.appendLn("");

		assertThat(s, equalTo(""));

	}


	@Test
	public void testAppendLn_引数を一つだけ渡すとそのままの文字列が返ってくる(){

		String s = Util.appendLn("test");

		assertThat(s, equalTo("test"));

	}

	@Test
	public void testAppendLn_引数を複数渡すと改行区切りで文字列が返ってくる(){

		String s = Util.appendLn("test","test2","test3");

		final String BR = System.getProperty("line.separator");
		assertThat(s, equalTo("test" + BR + "test2" + BR + "test3"));

	}


	private class SessionController extends Controller{
		void deleteUserSession(){
			session.remove("id");
		}

		void addUserSession(String id) {
			session.put("id", id);
		}
	}

}
package unit.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.number.OrderingComparisons.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparisons.lessThanOrEqualTo;

import java.text.ParseException;

import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class UserTest extends UnitTest {



	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}


	@Test
	public void ユーザーの通常登録() throws ParseException{

		User user = new User();
		user.id = "user1Id";
		user.name = "user1Name";
		user.save();

		User assertUser = User.findById("user1Id");
		assertThat(assertUser, notNullValue());
		assertThat(assertUser.name, is("user1Name"));

	}


	@Test
	public void testCompareTo() throws ParseException{

		User user = new User();
		user.id = "user1Id";
		user.name = "user1Name";


		User user2 = new User();
		user2.id = "user2Id";
		user2.name = "AAAuser2Name";

		assertThat(user.compareTo(user2), is(greaterThanOrEqualTo(1)));
		assertThat(user2.compareTo(user), is(lessThanOrEqualTo(-1)));

	}



}

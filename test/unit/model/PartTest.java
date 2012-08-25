package unit.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.number.OrderingComparisons.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparisons.lessThanOrEqualTo;

import java.text.ParseException;

import models.Part;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import sun.print.resources.serviceui;
import sun.security.action.PutAllAction;

public class PartTest extends UnitTest {



	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}



	@Test
	public void testIsParticipating() {

		Part part = new Part();
		part.is_in_recruitment = false;
		part.participant = null;
		assertThat(part.isParticipating(), is(false));

		part.is_in_recruitment = true;
		part.participant = null;
		assertThat(part.isParticipating(), is(false));

		part.is_in_recruitment = true;
		part.participant = new User();
		assertThat(part.isParticipating(), is(true));

	}



	@Test
	public void testIsWanting(){

		Part part = new Part();
		part.is_in_recruitment = false;
		part.participant = null;
		assertThat(part.isWanting(), is(false));

		part.is_in_recruitment = true;
		part.participant = null;
		assertThat(part.isWanting(), is(true));

		part.is_in_recruitment = true;
		part.participant = new User();
		assertThat(part.isWanting(), is(false));

	}



	@Test
	public void testGetParticipantName() {

		Part part = new Part();
		part.is_in_recruitment = false;
		part.participant = null;
		assertThat(part.getParticipantName(), is("-"));

		part.is_in_recruitment = true;
		part.participant = null;
		assertThat(part.getParticipantName(), is("募集中"));

		part.is_in_recruitment =true;
		User user = new User();
		user.name = "username";
		part.participant = user;
		assertThat(part.getParticipantName(), is("username"));

	}

	@Test
	public void testToString() {
		Part part = new Part();
		part.participant = null;
		assertThat(part.toString(), is(""));

		User user = new User();
		user.name = "username";
		part.participant = user;
		assertThat(part.toString(), is("username"));

	}
}

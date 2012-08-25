package unit.model;

import static org.hamcrest.CoreMatchers.is;
import models.EventPart;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class EventPartTest extends UnitTest {



	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}


	@Test
	public void testToString() {
		EventPart eventPart = new EventPart("guitar");
		assertThat(eventPart.toString(), is("guitar"));

	}

}

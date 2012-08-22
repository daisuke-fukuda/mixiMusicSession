package unit.model;

import static org.hamcrest.CoreMatchers.equalTo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.EventPart;

import org.junit.Test;

import play.mvc.Before;
import play.test.Fixtures;
import play.test.UnitTest;

public class EventTest extends UnitTest {



	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}



	@Test
	public void イベントの通常登録() throws ParseException{

		Event event1 = イベント1を返す();

		event1.save();

		Event assertEvent = Event.find("byCommunityId", 100l).first();
		assertThat(assertEvent.title, equalTo("データ1"));
		assertThat(assertEvent.toString(), equalTo("データ1"));

	}





	public static Event イベント1を返す() throws ParseException{
		Event event = new Event();
		event.title = "データ1";
		event.communityId = 100l;

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		event.startAt = format.parse("2012/08/01 12:00");
		event.endAt = format.parse("2012/08/01 18:00");
		event.place = "場所1";
		event.content = "内容1hogehoge";

		List<EventPart> wantedParts = new ArrayList<EventPart>();
		wantedParts.add(new EventPart("Vo"));
		wantedParts.add(new EventPart("Gt"));
		wantedParts.add(new EventPart("Ba"));
		wantedParts.add(new EventPart("Dr"));
		wantedParts.add(new EventPart("Key"));
		event.wantedParts = wantedParts;

		return event;

	}


}

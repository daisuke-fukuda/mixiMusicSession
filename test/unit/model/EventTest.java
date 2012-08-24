package unit.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.EventPart;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import util.Const;

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
		assertThat(assertEvent.song.size(), equalTo(0));
		assertThat(assertEvent.participants.size(), equalTo(0));
		assertThat(assertEvent.wantedParts.size(), equalTo(5));

	}



	@Test
	public void testAddWantedParts_nullを渡した場合何も起きない(){

		Event event = new Event();
		event.addWantedParts(null);
		event.save();

		assertThat(event.wantedParts.size(), equalTo(0));

	}


	@Test
	public void testAddWantedParts_0件配列を渡した場合何も起きない(){

		Event event = new Event();
		event.addWantedParts(new String[0] );
		event.save();

		assertThat(event.wantedParts.size(), equalTo(0));

	}

	@Test
	public void testAddWantedParts_配列を渡した場合wantedPartNamesに格納される(){

		Event event = new Event();
		event.addWantedParts(new String[]{"Gt", "Ba", "Dr"});
		event.save();

		assertThat(event.wantedParts.size(), equalTo(3));
		assertThat(event.wantedParts.get(0).partName, equalTo("Gt"));
		assertThat(event.wantedParts.get(1).partName, equalTo("Ba"));
		assertThat(event.wantedParts.get(2).partName, equalTo("Dr"));

	}


	@Test
	public void testGetParticipantsTargetList() throws ParseException{


		Event event1 = イベント1を返す();

		User user1 = new User();
		user1.id = "user1Id";
		user1.name = "ZZuser1Name";
		event1.participants.add(user1);

		User user2 = new User();
		user2.id = "user2Id";
		user2.name = "AAuser2Name";
		event1.participants.add(user2);

		event1.save();


		List<User> list = event1.getParticipantsTargetList();

		assertThat( list.size(), is(3));
		assertThat(list.get(0).id, is(Const.USER_ID_DUMMY));
		assertThat(list.get(0).name,is(Const.USER_NAME_DUMMY));
		assertThat(list.get(1).id, is("user2Id"));
		assertThat(list.get(1).name,is("AAuser2Name"));
		assertThat(list.get(2).id, is("user1Id"));
		assertThat(list.get(2).name,is("ZZuser1Name"));




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

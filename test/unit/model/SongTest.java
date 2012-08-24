package unit.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.EventPart;
import models.Part;
import models.Song;
import models.User;
import net.sf.oval.constraint.NotEqual;

import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;
import util.Const;
import util.Util;

public class SongTest extends UnitTest {



	@Before
	public void setup() throws ParseException {
		Fixtures.deleteDatabase();
		//イベントの作成
		Event event1 = EventTest.イベント1を返す();
		event1.save();

		//曲の作成
		Song song1 = new Song(event1);
		song1.title = "タイトル1";
		song1.singer = "歌手1";

		song1.save();
	}


	@Test
	public void 候補曲の作成() throws ParseException{

		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();
		assertThat(assertSong.title, equalTo("タイトル1"));

		List<Part> assertParts = assertSong.parts;
		assertThat(assertParts.get(0).eventPart.partName, equalTo("Vo"));
		assertThat(assertParts.get(1).eventPart.partName, equalTo("Gt"));
		assertThat(assertParts.get(2).eventPart.partName, equalTo("Ba"));
		assertThat(assertParts.get(3).eventPart.partName, equalTo("Dr"));
		assertThat(assertParts.get(4).eventPart.partName, equalTo("Key"));

	}



	@Test
	public void testFindPartByEventPartId_通常() throws ParseException {


		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();

		EventPart vo = EventPart.find("byPartName", "Vo").first();
		assertThat(vo.partName,is(assertSong.findPartByEventPartId(vo.id).eventPart.partName));

		EventPart gt = EventPart.find("byPartName", "Gt").first();
		assertThat(gt.partName,is(assertSong.findPartByEventPartId(gt.id).eventPart.partName));


	}


	@Test
	public void testFindPartByEventPartId_見つからなかったらを返す() throws ParseException {

		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();
		assertThat(assertSong.findPartByEventPartId(-1l), is(nullValue()));

	}


	@Test
	public void testFindPartByEventPartId_nullを渡すとnullを返す() throws ParseException {

		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();
		assertThat(assertSong.findPartByEventPartId(null), is(nullValue()));

	}



	@Test
	public void testreplaceRecruitmentParts_nullを渡すとリセットされる() throws ParseException {


		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();
		assertSong.replaceRecruitmentParts(null);

		assertThat(assertSong.parts, is(notNullValue()));
		for (Part part : assertSong.parts){
			assertThat(part.is_in_recruitment , is(false));
		}

	}


	@Test
	public void testReplaceRecruitmentParts_渡したEventPartが募集中になる() throws ParseException {


		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();

		List<Long> eventPartIds = new ArrayList();
		for ( EventPart eventPart : assertSong.event.wantedParts) {
			eventPartIds.add(eventPart.id);
		}


		assertSong.replaceRecruitmentParts(eventPartIds);

		assertThat(assertSong.parts, is(notNullValue()));
		for (Part part : assertSong.parts){
			assertThat(part.is_in_recruitment , is(true));
		}

	}


	@Test
	public void testSetParticipateParts() throws ParseException {


		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();

		List<Long> eventPartIds = new ArrayList();
		for ( EventPart eventPart : assertSong.event.wantedParts) {
			//ボーカルに参加する
			if ("Vo".equals(eventPart.partName)) {
				eventPartIds.add(eventPart.id);
			}
		}

		assertSong.setParticipateParts(eventPartIds);

		assertThat(assertSong.parts, is(notNullValue()));
		for (Part part : assertSong.parts){
			if ("Vo".equals(part.eventPart.partName)) {
				assertThat(part.participant, is(equalTo(Util.getLoginUser())));
			} else {
				assertThat(part.participant, is(nullValue()));
			}
		}
	}


	@Test
	public void testFindPartsNames() throws ParseException {


		//テスト
		Song assertSong = Song.find("byTitle", "タイトル1").first();

		List<Long> eventPartIds = new ArrayList();
		for ( EventPart eventPart : assertSong.event.wantedParts) {
			if ("Vo".equals(eventPart.partName)
					|| "Gt".equals(eventPart.partName)) {
				eventPartIds.add(eventPart.id);
			}
		}
		assertThat(assertSong.findPartsNames(eventPartIds), is(equalTo("Vo, Gt")));

	}



	@Test
	public void testSetParticipants() {


		User user1 = new User();
		user1.id = "user1";
		user1.save();

		User user2 = new User();
		user2.id = "user2";
		user2.save();


		List<String> participantIdsList = new ArrayList<String>();
		participantIdsList.add(Const.USER_ID_DUMMY);
		participantIdsList.add(user1.id);
		participantIdsList.add(Const.USER_ID_DUMMY);
		participantIdsList.add(user2.id);
		participantIdsList.add(Const.USER_ID_DUMMY);


		Song assertSong = Song.find("byTitle", "タイトル1").first();
		assertSong.setParticipants(participantIdsList);



		assertThat(assertSong.parts.get(0).participant, is(nullValue()));
		assertThat(assertSong.parts.get(1).participant.id, is("user1"));
		assertThat(assertSong.parts.get(2).participant, is(nullValue()));
		assertThat(assertSong.parts.get(3).participant.id, is("user2"));
		assertThat(assertSong.parts.get(4).participant, is(nullValue()));



	}

}

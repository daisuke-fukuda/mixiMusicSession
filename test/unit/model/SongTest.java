package unit.model;

import static org.hamcrest.CoreMatchers.equalTo;

import java.text.ParseException;
import java.util.List;

import models.Event;
import models.EventPart;
import models.Part;
import models.Song;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class SongTest extends UnitTest {



	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}




	@Test
	public void findPartByEventPartIdのテスト() throws ParseException{


		//イベントの作成
		Event event1 = EventTest.イベント1を返す();
		event1.save();


		//曲の作成
		Song song1 = new Song(event1);

		//テスト
		for ( EventPart eventPart : event1.wantedParts){
			assertThat(song1.findPartByEventPartId(eventPart.id).eventPart.id, equalTo(eventPart.id));
		}

	}



	@Test
	public void 候補曲の作成() throws ParseException{


		//イベントの作成
		Event event1 = EventTest.イベント1を返す();
		event1.save();


		//曲の作成
		Song song1 = new Song(event1);
		song1.title = "タイトル1";
		song1.singer = "歌手1";

		song1.save();


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






}

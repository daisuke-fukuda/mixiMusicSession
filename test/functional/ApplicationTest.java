package functional;

import static org.hamcrest.CoreMatchers.notNullValue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.EventPart;

import org.junit.Test;

import play.mvc.Controller;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;
import unit.model.EventTest;
import controllers.Application;

public class ApplicationTest extends FunctionalTest {

//
//
//	@Before
//	public void setup() {
//		Fixtures.deleteDatabase();
//
//		//ログイン
//		class DummyLogin extends Controller{
//			void login() {
//				session.put("id", "123");
//			}
//		}
//		new DummyLogin().login();
//
//	}
//
//
//
//	@Test
//	public void 曲の登録() throws ParseException{
//
//		//マスタデータ登録
//		EventTest.イベント1を返す().save();
//
//		Event event = Event.find("byTitle", "データ1").first();
//		assertThat(event, notNullValue());
//
//
//		//曲の登録
//
//		List<EventPart> eventPart = event.wantedParts;
//		List<Long> wantedParts = new ArrayList();
//
//		//募集パートはVoとBa
//		for (EventPart wantedPart : eventPart){
//			if("Vo".equals(wantedPart.partName)
//					|| "Ba".equals(wantedPart.partName)){
//				wantedParts.add(wantedPart.id);
//			}
//		}
//
//		//実行
//
//
//		Response response = GET("/Application/createSongSave");
//        assertStatus(200, response);
//
//        Application.createSongSave(event.id
//							,"曲の登録テストタイトル"
//							,"曲の登録テスト歌手"
//							,"曲の登録テスト備考"
//							,wantedParts);

//
//
//
//		//確認
//		Song assertSong = Song.find("byTitle", "曲の登録テスト").first();
//		assertThat(assertSong, notNullValue());
//
//
//		assertThat(assertSong.title, equalTo("曲の登録テスト"));
//		assertThat(assertSong.singer, equalTo("曲の登録テスト歌手"));
//		assertThat(assertSong.remarks, equalTo("曲の登録テスト備考"));
//
//
//
//
//	}
}

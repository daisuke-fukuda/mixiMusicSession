package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.Part;
import models.Song;
import models.User;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;
import play.mvc.With;
import util.Util;
import util.SongState;


@With(Auth.class)
public class Application extends Controller {





    public static void index() {

    	List<Event> eventList = Event.findAll();
        render(eventList);
    }


	public static void createEvent(){
		render();
	}




	public static void createEventSave(Event event
										, String date
										, String startAtTime
										, String endAtTime
										, String wantedParts) throws ParseException{


		//時刻を編集
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		if ( StringUtils.isNotBlank(startAtTime)){
			event.startAt = format.parse(date + " " + startAtTime);
		}
		if ( StringUtils.isNotBlank(endAtTime)){
			event.endAt = format.parse(date + " " + endAtTime);
		}
		//募集パートを分解
		String[] wantedPartsArray = wantedParts.split(" +");
		event.setWantedPartsByArray(wantedPartsArray);

		// 参加者に主催者を登録
		event.participants.add(Util.getLoginUser());

		event.save();
		flash.success("作成しました。");
		createEvent();
	}




	public static void participateEvent(Long id) {
		Event event = Event.findById(id);
		event.participants.add(Util.getLoginUser());
		event.save();

		index();
	}



    public static void deleteEvent(Long id) {

    	Event.findById(id)._delete();
    	index();

    }


    public static void event(Long eventId) {

    	Event event = Event.findById(eventId);
    	List<Song> songList = Song.find("byEvent", event).fetch();
        render(event,songList);
    }



	public static void createSong(Long eventId) {

		Event event = Event.findById(eventId);
		render(event, eventId);

	}




	public static void createSongSave(Long eventId
	                                  , String title
	                                  , String singer
	                                  , String remarks
	                                  , List<Long> wantedParts
	                                  , List<Long> participateParts
	                                  ) {

		//TODO バリデーション


		//保存
		Event event = Event.findById(eventId);
		Song song = new Song(event);
		song.state = SongState.IN_WANTING;
		song.title = title;
		song.singer = singer;
		song.remarks = remarks;
		song.setRecruitmentParts(wantedParts);
		song.setParticipateParts(participateParts);
		song.save();
		flash.success("作成しました。");



		/*
		 * 結果イメージ
		 *
		 * ★【新規】**************************************
		 * 曲名：hogehoge
		 * 状況：募集中
		 * 希望パート:Vo
		 * 募集パート:Gt1,Gt2,Key
		 * 備考：hogehoge
		 * ************************************************
		 */

		String result = Util.appendLn
				("★【新規】**************************************"
				, "曲名：" + song.title
				, "状況：" + song.state.getName()
				,"希望パート：" + song.findPartsName(participateParts)
				,"募集パート：" + song.findIsInRecruitmentPartsName()
				,"備考：" + song.remarks
				,"************************************************"
				);
		flash.put("result", result);


		createSong(eventId);

	}
	public static void deleteSong(Long songId, Long eventId) {
		Song.findById(songId)._delete();

		flash.success("削除しました。");
		event(eventId);

	}

	public static void editSong(Long songId) {

		Song song = Song.findById(songId);

		Event event = song.event;
    	List<Song> songList = Song.find("byEvent", event).fetch();


		render(song ,songList, event);

	}

	public static void editSongSave( Long songId
					, SongState state
					, String title
		            , String singer
		            , String remarks
		            , List<Long> wantedParts
		            , List<String> participants
		            ) {
		//TODO バリデーション



		// 入力値の編集
		Song song = Song.findById(songId);
		song.state = state;
		song.title = title;
		song.singer = singer;
		song.remarks = remarks;
		song.setRecruitmentParts(wantedParts);
		song.setParticipants(participants);

		// 保存
		song.save();
		flash.success("変更しました。");



		/*
		 * 結果イメージ
		 *
		 * ★【変更】**************************************
		 * 曲名：hogehoge
		 * 状況：募集中
		 * 募集パート:Gt1,Gt2,Key
		 * 備考：hogehoge
		 * ************************************************
		 */
		String result = Util.appendLn
				("★【変更】**************************************"
				, "曲名：" + song.title
				, "状況：" + song.state.getName()
				,"募集パート：" + song.findIsInRecruitmentPartsName()
				,"備考：" + song.remarks
				,"************************************************"
				);
		flash.put("result", result);


		editSong(song.id);

	}





	public static void participateSong(Long songId){

		Song song = Song.findById(songId);
	  	List<Song> songList = Song.find("byEvent", song.event).fetch();




		render(song, songList);

	}


	public static void participateSongSave(Long songId
									, SongState state
									, String title
									, String singer
									, String remarks
									, List<Long> participateParts
									) {
		//TODO バリデーション


		// 入力値の編集
		Song song = Song.findById(songId);
		song.state = state;
		song.remarks = remarks;
		song.setParticipateParts(participateParts);

		// 保存
		song.save();
		flash.success("便乗しました。");

		/*
		 *
		 * 結果イメージ
		 *
		 * ★【便乗】**************************************
		 * 曲名：hogehoge
		 * 希望パート:Vo
		 * 募集パート:Gt1,Gt2,Key
		 * 備考：hogehoge
		 * ************************************************
		 */
		String result = Util.appendLn
				("★【便乗】**************************************"
				, "曲名：" + song.title
				, "状況：" + song.state.getName()
				,"希望パート：" + song.findPartsName(participateParts)
				,"募集パート：" + song.findIsInRecruitmentPartsName()
				,"備考：" + song.remarks
				,"************************************************"
				);
		flash.put("result", result);


		participateSong(song.id);

	}








}





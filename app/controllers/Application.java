package controllers;

import java.util.List;

import models.Event;
import models.Song;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import util.CustomList;
import util.SongState;


@With(Auth.class)
public class Application extends Controller {





    public static void index() {

    	List<Event> eventList = Event.findAll();
        render(eventList);
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



	public static void deleteSong(Long songId, Long eventId) {
		Song.findById(songId)._delete();

		flash.success("削除しました。");
		event(eventId);

	}

	public static void editSong(Long songId) {

		Song song = Song.findById(songId);

		Event event = song.event;
    	List<Song> songList = Song.find("byEvent", event).fetch();


		render(song,songList);

	}

	public static void editSongSave(Song song) {
		song.save();
		flash.success("変更しました。");






		/*
		 *
		 * 結果イメージ
		 *
		 * ★【変更】**************************************
		 * 曲名：hogehoge
		 * 状況：募集中
		 * 募集パート:Gt1,Gt2,Key
		 * 備考：hogehoge
		 * ************************************************
		 */

		StringBuilder result = new StringBuilder();
		String BR = System.getProperty("line.separator");
		result.append("★【変更】**************************************");
		result.append(BR);
		result.append("曲名：" + song.title);
		result.append(BR);

		result.append("状況：" + song.state.getName());
		result.append(BR);


		List<String> wantedPartList = new CustomList<String>();
		if ( song.vocal.isWanting() ){
			wantedPartList.add("Vo");
		}
		if (song.guitar1.isWanting()) {
			wantedPartList.add("Gt1");
		}
		if (song.base.isWanting()) {
			wantedPartList.add("Ba");
		}
		if (song.drums.isWanting()) {
			wantedPartList.add("Dr");
		}
		if (song.keybord1.isWanting()) {
			wantedPartList.add("Key1");
		}
		result.append("募集パート：" + wantedPartList.toString());
		result.append(BR);

		result.append("備考：" + song.remarks);
		result.append(BR);

		result.append("************************************************");
		flash.put("result", result.toString());




		editSong(song.id);

	}


	public static void createSong(Long eventId) {

		render(eventId);

	}

	public static void createSongSave(Long eventId
	                                  , Song song
	                                  , boolean is_desire_vocal
	                                  , boolean is_desire_guitar1
	                                  , boolean is_desire_base
	                                  , boolean is_desire_drums
	                                  , boolean is_desire_keybord1) {



		song.event = Event.findById(eventId);
		song.state = SongState.IN_WANTING;
		if(is_desire_vocal){
			song.vocal.setParticipant(session.get("username"));
		}
		if(is_desire_guitar1){
			song.guitar1.setParticipant(session.get("username"));
		}
		if(is_desire_base){
			song.base.setParticipant(session.get("username"));
		}
		if(is_desire_drums){
			song.drums.setParticipant(session.get("username"));
		}
		if(is_desire_keybord1){
			song.keybord1.setParticipant(session.get("username"));
		}


		song.save();
		flash.success("作成しました。");




		/*
		 *
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

		StringBuilder result = new StringBuilder();
		String BR = System.getProperty("line.separator");
		result.append("★【新規】**************************************");
		result.append(BR);
		result.append("曲名：" + song.title);
		result.append(BR);

		result.append("状況：" + song.state.getName());
		result.append(BR);


		List<String> desiredPartList = new CustomList<String>();

		if (song.vocal.isParticipating()){
			desiredPartList.add("Vo");
		}
		if (song.guitar1.isParticipating()) {
			desiredPartList.add("Gt1");
		}
		if (song.base.isParticipating()) {
			desiredPartList.add("Ba");
		}
		if (song.drums.isParticipating()) {
			desiredPartList.add("Dr");
		}
		if (song.keybord1.isParticipating()) {
			desiredPartList.add("Key1");
		}

		result.append("希望パート：" + desiredPartList.toString());
		result.append(BR);


		List<String> wantedPartList = new CustomList<String>();
		if ( song.vocal.isWanting() ){
			wantedPartList.add("Vo");
		}
		if (song.guitar1.isWanting()) {
			wantedPartList.add("Gt1");
		}
		if (song.base.isWanting()) {
			wantedPartList.add("Ba");
		}
		if (song.drums.isWanting()) {
			wantedPartList.add("Dr");
		}
		if (song.keybord1.isWanting()) {
			wantedPartList.add("Key1");
		}
		result.append("募集パート：" + wantedPartList.toString());
		result.append(BR);

		result.append("備考：" + song.remarks);
		result.append(BR);

		result.append("************************************************");
		flash.put("result", result.toString());




		createSong(eventId);

	}







	public static void participateSong(Long songId){

		Song song = Song.findById(songId);
	  	List<Song> songList = Song.find("byEvent", song.event).fetch();




		render(song, songList);

	}


	public static void participateSongSave(Song song
	                                  , boolean is_desire_vocal
	                                  , boolean is_desire_guitar1
	                                  , boolean is_desire_base
	                                  , boolean is_desire_drums
	                                  , boolean is_desire_keybord1) {



		if(is_desire_vocal){
			song.vocal.setParticipant(session.get("username"));
		}
		if(is_desire_guitar1){
			song.guitar1.setParticipant(session.get("username"));
		}
		if(is_desire_base){
			song.base.setParticipant(session.get("username"));
		}
		if(is_desire_drums){
			song.drums.setParticipant(session.get("username"));
		}
		if(is_desire_keybord1){
			song.keybord1.setParticipant(session.get("username"));
		}


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

		StringBuilder result = new StringBuilder();
		String BR = System.getProperty("line.separator");

		if(song.state == SongState.ESTABLISHED) {
			result.append("★【成立】**************************************");
		} else {
			result.append("★【便乗】**************************************");
		}
		result.append(BR);
		result.append("曲名：" + song.title);
		result.append(BR);

		result.append("状況：" + song.state.getName());
		result.append(BR);


		List<String> desiredPartList = new CustomList<String>();

		if (is_desire_vocal){
			desiredPartList.add("Vo");
		}
		if (is_desire_guitar1) {
			desiredPartList.add("Gt1");
		}
		if (is_desire_base) {
			desiredPartList.add("Ba");
		}
		if (is_desire_drums) {
			desiredPartList.add("Dr");
		}
		if (is_desire_keybord1) {
			desiredPartList.add("Key1");
		}

		result.append("希望パート：" + desiredPartList.toString());
		result.append(BR);


		List<String> wantedPartList = new CustomList<String>();
		if ( song.vocal.isWanting() ){
			wantedPartList.add("Vo");
		}
		if (song.guitar1.isWanting()) {
			wantedPartList.add("Gt1");
		}
		if (song.base.isWanting()) {
			wantedPartList.add("Ba");
		}
		if (song.drums.isWanting()) {
			wantedPartList.add("Dr");
		}
		if (song.keybord1.isWanting()) {
			wantedPartList.add("Key1");
		}
		result.append("募集パート：" + wantedPartList.toString());
		result.append(BR);

		result.append("備考：" + song.remarks);
		result.append(BR);

		result.append("************************************************");
		flash.put("result", result.toString());




		participateSong(song.id);

	}





	public static void createEvent(){
		render();
	}




	public static void createEventSave(Event event){
		event.save();
		flash.success("作成しました。");
		createEvent();
	}












//	public static void createSongOld(Long eventId) {
//
//		render(eventId);
//
//	}
//
//	public static void createSongOldSave(Long eventId, Song song) {
//
//		song.event = Event.findById(eventId);
//		song.save();
//		flash.success("作成しました。");
//
//
//
//
//		/*
//		 *
//		 * 結果イメージ
//		 *
//		 * ★【新規】**************************************
//		 * 曲名：hogehoge
//		 * 希望パート:Vo
//		 * 募集パート:Gt1,Gt2,Key
//		 * 備考：hogehoge
//		 * ************************************************
//		 */
//
//		StringBuilder result = new StringBuilder();
//		String BR = System.getProperty("line.separator");
//		result.append("★【新規】**************************************");
//		result.append(BR);
//		result.append("曲名：" + song.title);
//		result.append(BR);
//
//		List<String> desiredPartList = new CustomList<String>();
//
//		if (song.vocal.isParticipating()){
//			desiredPartList.add("Vo");
//		}
//		if (song.guitar1.isParticipating()) {
//			desiredPartList.add("Gt1");
//		}
//		if (song.base.isParticipating()) {
//			desiredPartList.add("Ba");
//		}
//		if (song.drums.isParticipating()) {
//			desiredPartList.add("Dr");
//		}
//		if (song.keybord1.isParticipating()) {
//			desiredPartList.add("Key1");
//		}
//
//		result.append("希望パート：" + desiredPartList.toString());
//		result.append(BR);
//
//
//		List<String> wantedPartList = new CustomList<String>();
//		if ( song.vocal.isWanting() ){
//			wantedPartList.add("Vo");
//		}
//		if (song.guitar1.isWanting()) {
//			wantedPartList.add("Gt1");
//		}
//		if (song.base.isWanting()) {
//			wantedPartList.add("Ba");
//		}
//		if (song.drums.isWanting()) {
//			wantedPartList.add("Dr");
//		}
//		if (song.keybord1.isWanting()) {
//			wantedPartList.add("Key1");
//		}
//		result.append("募集パート：" + wantedPartList.toString());
//		result.append(BR);
//
//		result.append("備考：" + song.remarks);
//		result.append(BR);
//
//		result.append("************************************************");
//		flash.put("result", result.toString());
//
//		createSongOld(eventId);
//
//	}


}





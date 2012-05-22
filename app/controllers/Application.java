package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {

    	List<Event> eventList = Event.findAll();
        render(eventList);
    }


    public static void event(Long evendId) {

    	Event event = Event.findById(evendId);
    	List<Song> songList = Song.find("byEvent", event).fetch();
        render(event,songList);
    }


	public static void editSong(Long songId) {

		Song song = Song.findById(songId);
		render(song);

	}

	public static void editSongSave(Song song) {
		System.out.println(song.id);
		song.save();
		editSong(song.id);

	}


}

package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Song extends Model {


	private final String NOW_RECRUITMENT = "募集中";
	private final String NOT_RECRUITMENT = "-";


	// タイトル
	@Required
	public String title;

	public String singer;

	@ManyToOne
	public Event event;


	public Part vocal;

	public Part guitar1;

//	public String guitar2;

	public Part drums;

	public Part base;

	public Part keybord1;

//	public String keybord2;

//	public String other1;

//	public String other2;

	public String remarks;

	public String state;



	@Override
	public String toString() {

		return title;
	}



}

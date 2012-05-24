package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import util.SongState;

@Entity
public class Song extends Model {




//	public Song() {
//		vocal = new Part("Vo");
//		guitar1 = new Part("Gt1");
//		drums = new Part("Dr");
//		base = new Part("Ba");
//		keybord1 = new Part("Key1");
//
//	}



	// タイトル
	@MaxSize(255)
	@Required
	public String title;

	@MaxSize(255)
	public String singer;

	@ManyToOne
	public Event event;

	@OneToOne(cascade=CascadeType.ALL)
	public Part vocal;

	@OneToOne(cascade=CascadeType.ALL)
	public Part guitar1;

//	public String guitar2;

	@OneToOne(cascade=CascadeType.ALL)
	public Part drums;

	@OneToOne(cascade=CascadeType.ALL)
	public Part base;

	@OneToOne(cascade=CascadeType.ALL)
	public Part keybord1;

//	public String keybord2;

//	public String other1;

//	public String other2;


	@MaxSize(255)
	public String remarks;

//	@MaxSize(255)
//	public String state;

	public SongState state;




	@Override
	public String toString() {

		return title;
	}



}

package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import net.sf.oval.guard.Post;

import org.apache.commons.lang.StringUtils;
import org.h2.engine.User;
import org.hibernate.annotations.Cascade;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.templates.JavaExtensions;

@Entity
public class Event extends Model {



	// タイトル
	@Required
	@MaxSize(255)
	public String title;

	public Date startAt;

	public Date endAt;

	@MaxSize(255)
	public String place;

	@MaxSize(10000)
	public String content;

	@OneToMany(mappedBy="event",cascade=CascadeType.ALL)
	public List<Song> song = new ArrayList<Song>();


	@Override
	public String toString() {

		return title;
	}


}

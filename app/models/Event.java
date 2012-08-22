package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Event extends Model {



	// タイトル
	@Required
	@MaxSize(255)
	public String title;

	public Long communityId;

	public Date startAt;

	public Date endAt;

	@MaxSize(255)
	public String place;

	@MaxSize(10000)
	public String content;

	@OneToMany(mappedBy="event",cascade=CascadeType.ALL)
	public List<Song> song = new ArrayList();

	@ManyToMany(cascade=CascadeType.ALL)
	public Set<User> participants = new TreeSet();

	/**
	 * 募集パート
	 */
	@OneToMany(cascade=CascadeType.ALL)
	public List<EventPart> wantedParts = new ArrayList<EventPart>();


	public void setWantedPartsByArray(String[] wantedPartsArray) {

		List<EventPart> wantedPartsList = new ArrayList();
		for (String wantedPartName : wantedPartsArray){
			wantedPartsList.add(new EventPart(wantedPartName));
		}

		this.wantedParts = wantedPartsList;

	}

	public List<User> getParticipantsTargetList(){

		List<User> list = new ArrayList();

		User notAssigned = new User();
		notAssigned.id = "dummyUser";
		notAssigned.name = "-";

		list.add(notAssigned);
		list.addAll(participants);
		return list;
		}


	@Override
	public String toString() {
		return title;
	}


}

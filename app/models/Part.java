package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity
public class Part extends Model {




	private final String NOW_RECRUITMENT = "募集中";
	private final String NOT_RECRUITMENT = "-";


	//募集するパートかどうか
	public boolean is_in_recruitment = false;


	@ManyToOne(cascade=CascadeType.ALL)
	public EventPart eventPart;

	@ManyToOne
	public User participant;


	/**
	 * @return 応募者がいるか
	 */
	public boolean isParticipating(){

		if (is_in_recruitment) {
			if (participant == null) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}

	}


	/**
	 * @return 募集中か
	 */
	public boolean isWanting(){

		if (is_in_recruitment) {
			if (participant == null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public String getParticipantName() {
		if (is_in_recruitment) {
			if (participant == null) {
				return NOW_RECRUITMENT;
			} else {
				return participant.name;
			}
		} else {
			return NOT_RECRUITMENT;
		}
	}

	public void setParticipant(User participant) {
		this.participant = participant;
	}

	@Override
	public String toString(){
		return participant==null ? "" : participant.name;
	}

}

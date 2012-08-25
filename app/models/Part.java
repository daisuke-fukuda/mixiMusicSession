package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.db.jpa.Model;
import util.Const;

@Entity
public class Part extends Model {




	//募集するパートかどうか
	public boolean is_in_recruitment = false;


	@ManyToOne(cascade=CascadeType.ALL)
	public EventPart eventPart;

	@ManyToOne
	public User participant;


	/**
	 * @return 応募者がいるか
	 */
	public boolean isParticipating() {

		if (is_in_recruitment && participant != null) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * @return 募集中か
	 */
	public boolean isWanting(){

		if (is_in_recruitment && participant == null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @return 応募者の名前。募集中なら「募集中」、募集してなければ「-」
	 */
	public String getParticipantName() {
		if (is_in_recruitment) {
			if (participant == null) {
				return Const.NOW_RECRUITMENT;
			} else {
				return participant.name;
			}
		} else {
			return Const.NOT_RECRUITMENT;
		}
	}


	@Override
	public String toString(){
		return participant == null ? "" : participant.name;
	}

}

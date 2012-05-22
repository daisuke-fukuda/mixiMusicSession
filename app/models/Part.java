package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Part extends Model {


	private final String NOW_RECRUITMENT = "募集中";
	private final String NOT_RECRUITMENT = "-";

	public boolean is_in_recruitment = true;;
	private String participant;


	public String getParticipant() {
		if (is_in_recruitment) {
			if (participant.isEmpty()) {
				return NOW_RECRUITMENT;
			} else {
				return participant;
			}
		} else {
			return NOT_RECRUITMENT;
		}
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	@Override
	public String toString(){
		return getParticipant();
	}

}

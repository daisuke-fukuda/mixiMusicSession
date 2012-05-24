package models;

import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;

import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity
public class Part extends Model {

	public Part(String name){
		this.name = name;
	}




	private final String NOW_RECRUITMENT = "募集中";
	private final String NOT_RECRUITMENT = "-";

	public boolean is_in_recruitment = false;

	@MaxSize(255)
	public String name = "";


	@MaxSize(255)
	private String participant = "";

	public boolean isParticipating(){

		if (is_in_recruitment) {
			if (StringUtils.isBlank(participant)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}

	}

	public boolean isWanting(){

		if (is_in_recruitment) {
			if (StringUtils.isBlank(participant)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public String getParticipant() {
		if (is_in_recruitment) {
			if (StringUtils.isBlank(participant)) {
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
		return participant==null ? "" : participant;
	}

}

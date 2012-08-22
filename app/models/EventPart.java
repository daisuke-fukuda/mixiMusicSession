package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class EventPart extends Model {


	public EventPart(String partName) {
		this.partName = partName;
	}


	public String partName;



	@Override
	public String toString(){
		return partName;
	}

}

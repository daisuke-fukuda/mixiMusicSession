package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity(name="user_info")
public class User extends GenericModel implements Comparable {

	@Id
	public String id;

	public String name;



	@Override
	public String toString() {

		return name;
	}



	public int compareTo(Object o) {
		User user = (User)o;
		return(this.name.compareTo(user.name));
	}



}

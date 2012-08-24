package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity(name="user_info")
public class User<T> extends GenericModel implements Comparable<T> {

	@Id
	public String id;

	public String name;



	@Override
	public String toString() {

		return name;
	}



	public int compareTo(T o) {
		User<T> user = (User<T>)o;
		return(this.name.compareTo(user.name));
	}



}

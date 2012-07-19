package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;
import play.mvc.Scope.Session;

@Entity(name="user_info")
public class User extends GenericModel {

	@Id
	public String id;

	public String name;



	@Override
	public String toString() {

		return name;
	}



}

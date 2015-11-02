package models;


import play.db.ebean.Model;
import javax.persistence.Entity;


@Entity
public class Team extends Model{

	private String name;
	private String[] members;
}

package models;

import play.db.ebean.Model;
import javax.persistence.Entity;

@Entity
public class Team extends Model{

	public String name;

	@OneToMany
	public List<Account> members;
}

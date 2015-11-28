package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team extends Model {
	@Id
	@Column(name = "teamID")
	private long id;

	@Column(name = "name")
	public String name;

	/**
	 * Team.members to access
	 */
	@OneToMany(mappedBy = "team", cascade= CascadeType.ALL)
	public List<Account> members;

	/**
	 * Team.stories to access
	 */
	@OneToMany(mappedBy = "team", cascade= CascadeType.ALL)
	public List<Sprint> sprints;

	public Long getID() {
		return this.id;
	}
}

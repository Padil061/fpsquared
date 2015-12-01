package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team extends Model {
	@Id
	@Column(name = "teamID")
	private long id;

	@Constraints.Required
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



	public static Model.Finder<Long, Team> find = new Model.Finder<Long, Team>(Team.class);

	public long getId() {
		return id;
	}
}

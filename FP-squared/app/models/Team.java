package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
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

	public List<Sprint> getOpenSprints() {
		List<Sprint> openSprints = new ArrayList<Sprint>();

		for(Sprint sprint : sprints) {
			if (sprint.finished == false) {
				openSprints.add(sprint);
			}
		}

		return openSprints;
	}

	public List<Sprint> getClosedSprints() {
		List<Sprint> closedSprints = new ArrayList<Sprint>();

		for(Sprint sprint : sprints) {
			if (sprint.finished == true) {
				closedSprints.add(sprint);
			}
		}

		return closedSprints;
	}
}

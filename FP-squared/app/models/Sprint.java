package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Sprint extends Model {
    @Id
    @Column( name = "sprintID")
    private long id;

    @Constraints.Required
    @Column( name = "name")
    public String name;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    @Constraints.Required
    @Column( name = "start")
    public Date start;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    @Constraints.Required
    @Column( name = "end")
    public Date end;

    @Column( name = "finished")
    public boolean finished;

    /**
     * Sprint.stories to access
     */
    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<Story> stories;

    @ManyToOne(cascade = CascadeType.ALL)
    public Team team;

    public static Model.Finder<Long, Sprint> find = new Model.Finder<Long, Sprint>(Sprint.class);
}

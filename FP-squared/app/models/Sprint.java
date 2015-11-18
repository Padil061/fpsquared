package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Sprint extends Model {
    @Id
    @Column( name = "sprintID")
    private long id;

    @Column( name = "name")
    public String name;

    @Column( name = "start")
    public Date start;

    @Column( name = "end")
    public Date end;

    /**
     * Sprint.stories to access
     */
    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<Story> stories;

    @ManyToOne(cascade = CascadeType.ALL)
    Team team;

}

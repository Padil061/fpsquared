package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Story {

    @Id
    @Column( name = "storyID")
    private long id;

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

    @ManyToOne(cascade= CascadeType.ALL)
    public Sprint sprint;

    /**
     *  Story.tasks to access
     */
    @OneToMany(mappedBy = "story", cascade= CascadeType.ALL)
    public List<Task> tasks;

    public static Model.Finder<Long, Story> find = new Model.Finder<Long, Story>(Story.class);
}

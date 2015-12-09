package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Story  extends Model {

    @Id
    @Column( name = "storyID")
    private long id;

    @Constraints.Required
    @Column( name = "name")
    public String name;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
    @Column( name = "start")
    public Date start;

    @Formats.DateTime(pattern = "yyyy-MM-dd")
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

    public List<Story> getOpenStories() {
        List<Story> openStories = new ArrayList<Story>();

        for(Story story : sprint.stories) {
            if (story.finished == false) {
                openStories.add(story);
            }
        }

        return openStories;
    }

    public static Model.Finder<Long, Story> find = new Model.Finder<Long, Story>(Story.class);
}

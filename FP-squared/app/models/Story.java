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

    public long getId() {return id; }

    public static Model.Finder<Long, Story> find = new Model.Finder<Long, Story>(Story.class);

    public List<Task> getTasksWithStatus(String status) {
        List<Task> tasksWithStatus = new ArrayList<>();

        for (Task task : tasks) {
            if (task.status.equals(status)) {
                tasksWithStatus.add(task);
            }
        }

        return tasksWithStatus;
    }
}

package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

@Entity
public class Task extends Model {

    @Id
    @Column( name = "taskID")
    private long id;

    @Constraints.Required
    @Column(name = "name")
    public String name;

    @Constraints.Required
    @Column(name = "status")
    public String status;

    /**
     * Task.comments to access
     */
    @OneToMany(mappedBy = "task", cascade= CascadeType.ALL)
    public List<Comment> comments;

    /**
     * Task.checklistItems to access
     */
    @OneToMany(mappedBy = "task", cascade= CascadeType.ALL)
    public List<ChecklistItem> checklistItems;

    @ManyToOne(cascade= CascadeType.ALL)
    public Story story;

    public long getId() {
        return id;
    }

    public static Model.Finder<Long, Task> find = new Model.Finder<Long, Task>(Task.class);
}

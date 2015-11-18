package models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Task extends Model {

    @Id
    @Column( name = "taskID")
    private long id;

    @Column(name = "name")
    public String name;

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
    Story story;
}

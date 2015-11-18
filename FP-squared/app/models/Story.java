package models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Story {

    @Id
    @Column( name = "storyID")
    private long id;

    @Column( name = "start")
    public Date start;

    @Column( name = "end")
    public Date end;

    @ManyToOne(cascade= CascadeType.ALL)
    Sprint sprint;

    /**
     *  Story.tasks to access
     */
    @OneToMany(mappedBy = "story", cascade= CascadeType.ALL)
    public List<Task> tasks;
}

package models;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
public class Comment extends Model {

    @Id
    @Column( name = "commentID")
    private long id;

    @Column(name = "text")
    public String text;

    @ManyToOne(cascade = CascadeType.ALL)
    Task task;
}

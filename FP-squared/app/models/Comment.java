package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class Comment extends Model {

    @Id
    @Column( name = "commentID")
    private long id;

    @Constraints.Required
    @Column(name = "text")
    public String text;

    @ManyToOne(cascade = CascadeType.ALL)
    public Task task;

    public static Model.Finder<Long, Comment> find = new Model.Finder<Long, Comment>(Comment.class);
}

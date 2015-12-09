package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    public Result createUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        account.save();
        session("connected", account.userName);
        return redirect(routes.Application.welcome(account.userName));
    }

    public Result createTeam() {
        Team team = Form.form(Team.class).bindFromRequest().get();
        team.save();

        return redirect(routes.Application.dashboard());
    }

    public Result createSprint() {
        Sprint sprint = Form.form(Sprint.class).bindFromRequest().get();

        Ebean.beginTransaction();
        try {
            Account account = Account.find.where().eq("userName", session().get("connected")).findUnique();
            Team team = account.team;

            team.sprints.add(sprint);

            team.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.dashboard());
    }

    public Result createStory() {
        Story story = Form.form(Story.class).bindFromRequest().get();
        Long SprintID = Long.valueOf(session().get("sprintID")).longValue();

        Ebean.beginTransaction();
        try {
            Sprint sprint = Sprint.find.where().eq("sprintID", SprintID).findUnique();

            sprint.stories.add(story);

            sprint.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.sprintInfo(SprintID));
    }

    public Result closeStory() {
        Long sprintID = Long.valueOf(session().get("sprintID")).longValue();
        
        DynamicForm form = Form.form().bindFromRequest();
        Long id = Long.parseLong(form.get("id"));
        Ebean.beginTransaction();
        try {
            Story story = Story.find.byId(id);
            story.finished = true;

            story.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.sprintInfo(sprintID));
    }

    public Result verifyUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        session("connected", account.userName);

        return redirect(routes.Application.dashboard());
    }

    public Result logout() {
        session().clear();
        flash("success", "You've logged out successfully");
        return redirect(routes.Application.login());

    }

    public static String authenticateUser() {
        String user = session("connected");
        if(user != null) {
            return "Hello " + user + " welcome to your dashboard!";
        } else {
            return "Oops, you are not connected";
        }
    }

    public Result joinTeam() {
        DynamicForm form = Form.form().bindFromRequest();

        Long id = Long.parseLong(form.get("id"));
        Ebean.beginTransaction();
        try {
            Team team = Team.find.byId(id);
            Account account = Account.find.where().eq("userName", session().get("connected")).findUnique();
            account.team = team;

            account.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }

        return redirect(routes.Application.dashboard());
    }

    public Result closeSprint() {

        DynamicForm form = Form.form().bindFromRequest();
        Long id = Long.parseLong(form.get("id"));

        Ebean.beginTransaction();
        try {
            Sprint sprint = Sprint.find.byId(id);

            sprint.finished = true;

            sprint.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.dashboard());
    }

    public Result createTask() {
        Task task = Form.form(Task.class).bindFromRequest().get();

        session("task", Long.toString(task.getId()));

        Ebean.beginTransaction();
        try {
            Long storyId = Long.parseLong(session().get("story"));
            Story story = Story.find.byId(storyId);

            story.tasks.add(task);

            story.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.dashboard());
    }

    public Result createChecklistItem() {

        DynamicForm form = Form.form().bindFromRequest();

        String text = form.get("text");

        Ebean.beginTransaction();
        try {
            Long taskId = Long.parseLong(session().get("task"));
            Task task = Task.find.byId(taskId);

            ChecklistItem item = new ChecklistItem();
            item.text = text;
            item.task = task;
            item.save();

            task.checklistItems.add(item);
            task.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.dashboard());
    }

    //public Result deleteCheckListItem(){
      
      /*  DynamicForm form = Form.form().bindFromRequest();
        //Long id = Long.parseLong(form.get("id"));
        
        //Ebean.beginTransaction();
        //try{
            //ChecklistItem checklistItem = ChecklistItem.find.byId(id);
            checklistItem.delete();

            //SqlUpdate deleteCheckListeItems = Ebean.createSqlUpdate("DELETE from checklist_item WHERE checked = 1");
            //deleteCheckListeItems.execut();
        }
        finally{
            Ebean.endTransaction();
        }
        return redirect(routes.Application.dashboard());
        */
    //}

    public Result createComment() {

        DynamicForm form = Form.form().bindFromRequest();

        String text = form.get("text");

        Ebean.beginTransaction();
        try {
            Long taskId = Long.parseLong(session().get("task"));
            Task task = Task.find.byId(taskId);

            Comment comment = new Comment();
            comment.task = task;
            comment.text = text;
            comment.userName = session().get("connected");
            comment.save();

            task.comments.add(comment);
            task.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.dashboard());
    }

    public Result removeComment() {

        Ebean.beginTransaction();
        try {

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        return redirect(routes.Application.dashboard());
    }

    public Result login() { return ok(login.render()); }

    public Result dashboard() { return ok(dashboard.render()); }

    public Result sprintInfo(Long sprintID) {
        session("sprintID" , Long.toString(sprintID));
        return ok(sprint.render());
    }

    public Result welcome(String userName) {
        return ok(welcome.render(userName));
    }

}

package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.Routes;
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

        if (Account.find.where().eq("userName", account.userName).findUnique() != null) {
            return redirect(routes.Application.accountCreationFailed());
        }

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
        session("storyID", Long.toString(story.getId()));
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
        if (Account.authenticate(account.userName, account.password)) {
            session("connected", account.userName);
            return redirect(routes.Application.dashboard());
        } else {
            return redirect(routes.Application.failedLogin());
        }


    }

    public Result logout() {
        session().clear();
        flash("success", "You've logged out successfully");
        return redirect(routes.Application.login());

    }

    public static String authenticateUser() {
        String user = session("connected");//instance of singelton pattern
        if(user != null) {
            return "This is your dashboard. Feel free to explore, create and collaborate!";
        } else {
            return "Oops, you are not connected... something happened. Sorry about that!";
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

    public Result leaveTeam() {
        Ebean.beginTransaction();
        try {
            Account account = Account.find.where().eq("userName", session().get("connected")).findUnique();
            account.team = null;

            account.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }

        session().remove("sprintID");
        session().remove("storyID");

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

        Long storyId = Long.valueOf(session().get("storyID")).longValue();
        Long sprintID = Long.valueOf(session().get("sprintID")).longValue();

        Ebean.beginTransaction();
        try {
            Story story = Story.find.byId(storyId);

            story.tasks.add(task);

            story.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }


        return redirect(routes.Application.renderStory(sprintID, storyId));
    }

    public Result createChecklistItem() {

        DynamicForm form = Form.form().bindFromRequest();

        String text = form.get("text");

        Ebean.beginTransaction();
        try {
            Long taskId = Long.valueOf(form.get("taskID")).longValue();
            Task task = Task.find.byId(taskId);

            ChecklistItem item = new ChecklistItem();
            item.checked = false;
            item.text = text;
            item.task = task;
            item.save();

            task.checklistItems.add(item);
            task.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
        Long SprintID = Long.valueOf(session().get("sprintID")).longValue();
        return redirect(routes.Application.sprintInfo(SprintID));
    }

    public static void checkBoxChanged(Long checkListItemID) {
        Ebean.beginTransaction();
        try {
            ChecklistItem item = ChecklistItem.find.byId(checkListItemID);
            if (item.checked) {
                item.checked = false;
            }
            else {
                item.checked = true;
            }
            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }
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
            Long taskId = Long.valueOf(form.get("taskID")).longValue();
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
        Long SprintID = Long.valueOf(session().get("sprintID")).longValue();
        return redirect(routes.Application.sprintInfo(SprintID));
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

    public Result saveTaskStatus(Long taskId, String status) {
        Ebean.beginTransaction();
        try {
            Task task = Task.find.byId(taskId);

            task.status = status;
            task.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }

        return ok();
    }

    public Result saveChecklistItemChecked(Long checklistItemId, Boolean checked) {
        Ebean.beginTransaction();
        try {
            ChecklistItem item = ChecklistItem.find.byId(checklistItemId);

            item.checked = checked;

            item.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }

        return ok();
    }

    public Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Application.saveTaskStatus(),
                        controllers.routes.javascript.Application.saveChecklistItemChecked()
                )
        );
    }

    public Result login() { return ok(login.render()); }

    public Result dashboard() { return ok(dashboard.render()); }

    public Result sprintInfo(Long sprintID) {
        session("sprintID" , Long.toString(sprintID));
        return ok(sprint.render());
    }

    public Result renderStory(Long sprintID, Long storyID) {
        session("storyID", Long.toString(storyID));
        return ok(sprint.render());
    }
    public Result welcome(String userName) {
        return ok(welcome.render(userName));
    }

    public Result failedLogin() {
        return ok(failedlogin.render());
    }

    public Result accountCreationFailed() {
        return ok(accountcreationfailed.render());
    }
}

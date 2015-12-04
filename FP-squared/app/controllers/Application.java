package controllers;

import com.avaje.ebean.Ebean;
import models.Account;
import models.Sprint;
import models.Team;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

    public Result index() { return ok(index.render()); }

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

    public Result createSprint()
    {
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

        return redirect(routes.Application.teamDashboard(id));
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

    public Result changeView(String view) {
        Account account = Account.find.where().eq("userName", session().get("connected")).findUnique();

        session(view, account.userName);

        if(view.equals("sprint")) {
            return redirect(routes.Application.dashboard());
        }
        else if(view.equals("team")) {
        //    Team team = account.team;

            return redirect(routes.Application.dashboard());
        }
        else if(view.equals("story")) {
            return redirect(routes.Application.dashboard());
        }
        else {
            return redirect(routes.Application.dashboard());
        }
    }

    public Result login() { return ok(login.render()); }

    public Result dashboard() { return ok(dashboard.render()); }

    public Result teamDashboard(Long teamID) { return ok(teamdashboard.render(teamID)); }

    public Result sprintInfo() { return ok(sprint.render()); }

    public Result welcome(String userName) {
        return ok(welcome.render(userName));
    }

}

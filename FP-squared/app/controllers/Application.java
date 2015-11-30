package controllers;

import com.avaje.ebean.Ebean;
import models.Account;
import models.Team;
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

    public Result verifyUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        session("connected", account.userName);
        System.out.println(account.userName);
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
            return "Hello " + user + " you have started your session";
        } else {
            return "Oops, you are not connected";
        }
    }

    public Result joinTeam() {
        Team team = Form.form(Team.class).bindFromRequest().get();
        Ebean.beginTransaction();
        try {
            team = Team.find.where().eq("name", team.name).findUnique();
            Account account = Account.find.where().eq("userName", session().get("connected")).findUnique();
            account.team = team;

            account.save();

            Ebean.commitTransaction();
        } finally {
            Ebean.endTransaction();
        }

        String userName = session().get("connected");

        return redirect(routes.Application.teamDashboard(team.getId()));
    }

    public Result login() { return ok(login.render()); }

    public Result dashboard() { return ok(dashboard.render()); }

    public Result teamDashboard(Long teamID) { return ok(teamdashboard.render(teamID)); }

    public Result sprintInfo() { return ok(sprint.render()); }

    public Result welcome(String userName) {
        return ok(welcome.render(userName));
    }

}

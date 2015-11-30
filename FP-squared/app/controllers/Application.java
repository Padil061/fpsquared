package controllers;

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

        Long teamID = team.getID();

        return redirect(routes.Application.teamDashboard(teamID));
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
            return "Hello " + user + " you have started your session";
        } else {
            return "Oops, you are not connected";
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

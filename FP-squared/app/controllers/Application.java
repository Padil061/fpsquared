package controllers;

import models.Account;
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

    public Result verifyUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        session("connected", account.userName);

        return ok(dashboard.render());
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

    public Result dashboard() {
        return ok(dashboard.render());
    }

    public Result sprintInfo() { return ok(sprint.render()); }

    public Result welcome(String userName) {
        return ok(welcome.render(userName));
    }

}

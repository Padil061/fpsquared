package controllers;

import models.Account;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.dashboard;
import views.html.index;
import views.html.login;
import views.html.welcome;

public class Application extends Controller {

    public Result index() { return ok(index.render()); }

    public Result login() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        //session("connected", account.userName);

        return ok(login.render());
    }

    public Result logout() {
        session().clear();
        flash("success", "You've logged out successfully");
        return redirect(routes.Application.login());
    }

    public Result welcome(String userName) {
        return ok(welcome.render(userName));
    }

    public static String authenticateUser() {
        String user = session("connected");
        if(user != null) {
            return "Hello " + user + " you have started your session";
        } else {
            return "Oops, you are not connected";
        }
    }

    public Result dashboard() {
        return ok(dashboard.render());
    }

    public Result createUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        account.save();
        session("connected", account.userName);
        return redirect(routes.Application.welcome(account.userName));
    }

}

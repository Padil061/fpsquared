package controllers;

import models.Account;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.welcome;
import views.html.dashboard;

public class Application extends Controller {

    public Result index() { return ok(index.render()); }

    public Result welcome() {
        return ok(welcome.render());
    }

    public Result dashboard(Long id) {
        return ok(dashboard.render(id));
    }

    public Result createUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        account.save();
        return redirect(routes.Application.index());
    }

}

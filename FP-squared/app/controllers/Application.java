package controllers;

import models.Account;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public Result index() { return ok(index.render()); }

    public Result createUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        account.save();
        return redirect(routes.Application.index());
    }

}

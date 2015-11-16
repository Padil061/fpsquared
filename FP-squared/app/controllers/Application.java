package controllers;

import models.Account;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result createUser() {
        Account account = Form.form(Account.class).bindFromRequest().get();
        account.save();
        return redirect(routes.Application.index());
    }

    //public Result getPersons() {
        //List<Account> accounts = new Model.Finder(String.class, Account.class).all();
        //return ok(toJson(accounts));
    //}

}

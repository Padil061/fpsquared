package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

}
/*
public private Account {

  private username;
  private password;
  private static userId;

}

public class Team {
    // attributes and methods go in here
}

public class Sprint extends Controller {
    // attributes and methods go in here
}

public class Story extends Controller {
    // attributes and methods go in here
}

public class Tasks extends Controller {
    // attributes and methods go in here
}

*/

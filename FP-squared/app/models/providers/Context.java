package models.providers;

public class Context {
    private String[] storyStatusStates = {"Created", "Started", "In Review", "Complete"};

    private static Context ourInstance = new Context();

    public static Context getInstance() {
        return ourInstance;
    }

    private Context() {
    }

    public String[] getStoryStatusStates() {
        return storyStatusStates;
    }
}

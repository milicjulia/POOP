package javapoop;

public class Selections {

    private String path;
    private boolean activity;
    private String name;


    public Selections(String path, boolean activity,String name) {
        this.activity = activity;
        this.path = path;
        this.name=name;
    }

    public boolean getactivity() {
        return activity;
    }

    public String getpath() {
        return path;
    }
    
    public String getname() {
        return name;
    }

}

package javapoop;

import java.util.ArrayList;

public class Properties {

    private int width,height;
    private ArrayList<Sloj> slojevi=new ArrayList<>();


    public Properties(int width, int height, ArrayList<Sloj> slojevi) {
        this.width = width;
        this.height = height;
        for(Sloj s:slojevi) this.slojevi.add(s);
        
    }

    public int getwidth() {
        return width;
    }

    public int getheight() {
        return height;
    }

    public ArrayList<Sloj> getslojevi() {
        return slojevi;
    }

}
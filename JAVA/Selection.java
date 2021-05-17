package javapoop;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Selection{
ArrayList<Rectangle> sel=new ArrayList<>();
String ime;
boolean selektovana;

public Selection(String i,ArrayList<Rectangle> s) {
	ime=i;
	sel=s;
	selektovana=true;
}
public Selection(String i) {
	ime=i;
	selektovana=true;
}

public void addRectangle(Rectangle r) {
	sel.add(r);
}
public boolean isSelected() {
	return selektovana;
}
public void Selected(boolean b) {
	selektovana=b;
}

}

package javapoop;

import java.awt.image.BufferedImage;

public class Layer {
	BufferedImage layer;
	String ime;
	int height,width, opacity=100;
	boolean aktivan=true;
	
	public Layer() {}
	
	public BufferedImage getSloj() {return layer;}
	
	public Layer(BufferedImage image,String name) {
		layer=new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		layer=image;
		height=image.getHeight();
		width=image.getWidth();
		opacity=(int)image.getTransparency()*100;
		if(height>Slika.mheight) Slika.mheight=height;
		if(width>Slika.mwidth) Slika.mwidth=width;
		ime=name;
	}
	
	public void setHeight(int h) { height=h;}
	public void setWidth(int w) { width=w;}
	public void setOpacity(int o) { opacity=o;}
	public void setActivity(boolean a) { aktivan=a;}

	public int getHeight() {return height;}
	public int getWidth() {return width;}
	public int getOpacity() {return opacity;}
	public boolean getActivity() {return aktivan;}
	
	
}

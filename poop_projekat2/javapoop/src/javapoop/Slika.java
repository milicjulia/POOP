package javapoop;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

public class Slika {
	static ArrayList<Layer> slika=new ArrayList<>();
	static int mheight=0,mwidth=0;
	static String ime;
	static ArrayList<Selection> selekcije=new ArrayList<>();
	static Vector<Integer> br=new Vector<>();
	static Vector<Integer> param=new Vector<>();
	
	public Slika() {
		selekcije.add(new Selection("Cela"));
	}	
	
	public void setHeight(int h) { mheight=h;}
	public void setWidth(int w) { mwidth=w;}
	public void setName(String n) { ime=n;}
	public int getHeight() {return mheight;}
	public int getWidth() {return mwidth;}
	public String getName() {return ime;}
	
	public ArrayList<Selection> getSelections(){ return selekcije; }
	
	public BufferedImage getEndResult() {
		BufferedImage end=new BufferedImage(mwidth,mheight,BufferedImage.TYPE_INT_RGB);
		
		int[][] ostalop,red,green,blue;
		ostalop=new int[mheight][mwidth];
		red=new int[mheight][mwidth];
		green=new int[mheight][mwidth];
		blue=new int[mheight][mwidth];
		
		for (int i = 0; i < mheight; i++) {
			for (int j = 0; j < mwidth; j++) {
				red[i][j]=0;
				green[i][j]=0;
				blue[i][j]=0;
				ostalop[i][j]=100;
			}
		}
	
		for (int i = 0; i < mheight; i++) {
			for (int j = 0; j < mwidth; j++) {
				int color=0;
				for (Layer it:Slika.slika) {
					if(it.aktivan) {
						if(i<=(it.height-1) && j<=(it.width-1)) {
							int pixel = it.layer.getRGB(j, i);
							int crv = (pixel >> 16) & 0xff;
							int zel = (pixel >> 8) & 0xff;
							int pl = (pixel) & 0xff;
							double d=(double)it.getOpacity()*ostalop[i][j]/10000;
							ostalop[i][j] -= ostalop[i][j]*it.getOpacity() / 100;
					 //	  red[i][j] += crv *ostalop[i][j] * it.getOpacity() / 10000;
				//	green[i][j] += zel *ostalop[i][j] * it.getOpacity() / 10000;
				//	 blue[i][j] += pl *ostalop[i][j] * it.getOpacity() / 10000;
				//   color +=((red[i][j] & 0xff) << 16) + ((green[i][j] & 0xff) << 8) + (blue[i][j] & 0xff);
							color +=(((int)(crv*d) & 0xff) << 16) + (((int)(zel*d) & 0xff) << 8) + ((int)(pl*d) & 0xff);
						}
					}
				}
				// red[i][j] += 255*ostalop[i][j] /100;;
			//		green[i][j] += 255*ostalop[i][j] /100;;
			//		 blue[i][j] += 255*ostalop[i][j] /100;
			//	   color +=((red[i][j] & 0xff) << 16) + ((green[i][j] & 0xff) << 8) + (blue[i][j] & 0xff);	
				 end.setRGB(j, i,color);
			}
		}
		return end;
	}
	
}

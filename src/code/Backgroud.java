package code;

import java.awt.Graphics;

public class Backgroud {
	
	int x,y,y1=-700,height,width;
	GameStart gs=null;
      //�ҷ��ɻ�
//	boolean isLive;
	
	public Backgroud(int x, int y, int width, int height, GameStart gs) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.gs = gs;
	}


	
	//�������ķ���
	public void drawBG(Graphics g) {
	//����ͼƬ
	g.drawImage(gs.bgIMG, x, y, width,height,gs);
	g.drawImage(gs.bgIMG, x, y1, width,height,gs);
	y+=2;
	y1+=2;
	if(y1>=0) {
		y=0;
		y1=-700;
	}
	}
	
	

}

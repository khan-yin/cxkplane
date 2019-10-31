package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class EnemyBullet {
	int x,y,width,height;
	GameStart gs=null;
	boolean isLive;
	public EnemyBullet(int x, int y, int width, int height, GameStart gs, boolean isLive) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gs = gs;
		this.isLive = isLive;
	}
	public void drawenemybullet(Graphics g) {
		if(this.isLive==false) {
			return;
		}
		g.drawImage(gs.enemyBulletIMG, x, y,width,height, gs);
		y+=8; //�ӵ����ٶ�
	}
	//��ȡ�з��ӵ���λ��
	public Rectangle getenemyBullet(){
		return new Rectangle(x, y, width, height);
	}
	
	
}

package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class EnemyPlane {
	int x,y,width,height;
	boolean isLive;
	GameStart gs=null;
	public EnemyPlane(int x, int y, int width, int height, boolean isLive, GameStart gs) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isLive = isLive;
		this.gs = gs;
	}
	int djtime=0;
	double d=0.2;
	public void drawEnemyPlane(Graphics g){
		if(!this.isLive) {
			return;
		}
		g.drawImage(gs.enemyPlaneIMG, x, y, width,height,gs);
		y+=5;//敌方飞机的速度
		djtime++;
		if(djtime%30==0) {
		EnemyBullet enemybullet=new EnemyBullet(x+20, y+40, 10, 10, gs, true);
		//添加集合
		gs.enemyBulletList.add(enemybullet);
		djtime=0;
				}
	}
	
	//拿到敌方飞机的矩形框
			public Rectangle getenemy(){
				return new Rectangle(x, y, width, height);
			}
			
	
}

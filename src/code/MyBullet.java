package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class MyBullet {
		int x,y,width,height;
		GameStart gs=null;
		boolean isLive;
		public MyBullet(int x, int y, int width, int height, GameStart gs, boolean isLive) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.gs = gs;
			this.isLive = isLive;
		}
		//画出子弹
		public void drawMyBullet(Graphics g) {
			if(!this.isLive) {
				return;
			}
			g.drawImage(gs.myBulletIMG, x, y,width,height, gs);
			y-=8; //子弹的速度
		}
		
		//拿到我方子弹的矩形框
		public Rectangle getbullet(){
			return new Rectangle(x, y, width, height);
		}
		
		//判断子弹与飞机是否相交
		public void isIntersect(ArrayList<EnemyPlane> enemyList) {
			for(int i=0;i<enemyList.size();i++) {
				//取出每个敌机
				EnemyPlane enemy= enemyList.get(i);
				if(this.isLive==true&&enemy.isLive==true&&this.getbullet().intersects(enemy.getenemy())) {
					
					
					this.isLive=false;
					enemy.isLive=false;
					gs.score+=5;
					gs.bomb=new Bomb(enemy.x, enemy.y, 60, 60, gs);
					
				}
			}
		}
		
}

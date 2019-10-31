package code;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MyPlane {
	int x, y, width, height;
	
	boolean isLive;
	boolean U, D, L, R;
	GameStart gs = null;

	public MyPlane(int x, int y, int width, int height, boolean isLive, GameStart gs) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isLive = isLive;
		this.gs = gs;
	}

	public void drawPlane(Graphics g) {
		// 绘制图片
		if(this.isLive==false) {
			return;
		}
		g.drawImage(gs.myPlaneIMG, x, y, width, height, gs);
		if (U)
			if (y > 0)
				y -= 10;
		if (D)
			if (y < 600)
				y += 10;
		if (L)
			if (x >= -30)
				x -= 10;
		if (R)
			if (x < 400)
				x += 10;
	}

	// 飞机移动的方法
	public void movePlane(KeyEvent m) {
		
		if(!this.isLive) {
			return;
		}
		int code = m.getKeyCode(); // 拿到按键的值
		if (code == KeyEvent.VK_UP) {
	 		U = true;
		} else if (code == KeyEvent.VK_DOWN) {
			D = true;
		} else if (code == KeyEvent.VK_LEFT) {
			L = true;
		} else if (code == KeyEvent.VK_RIGHT) {
			R = true;
		} else if (code == KeyEvent.VK_SPACE) {
			// 制造篮球子弹
			
			
			
			
			MyBullet mybullet = new MyBullet(x + 50, y, 50, 50, gs, true);
			// 添加子弹
			
			gs.myBulletList.add(mybullet);
			}
			
		}

	

	// 飞机的停止
	public void stopPlane(KeyEvent m) {
		if(!this.isLive) {
			return;
		}
		int code = m.getKeyCode(); // 拿到按键的值
		if (code == KeyEvent.VK_UP) {
			U = false;
		} else if (code == KeyEvent.VK_DOWN) {
			D = false;
		} else if (code == KeyEvent.VK_LEFT) {
			L = false;
		} else if (code == KeyEvent.VK_RIGHT) {
			R = false;
		}

	}

	public Rectangle getmyPlane() {
		return new Rectangle(x, y, width, height);
	}

	public void isIntersect(ArrayList<EnemyBullet> enemyBulletList) {
		for (int i = 0; i < enemyBulletList.size(); i++) {
			EnemyBullet enemybullet = enemyBulletList.get(i);
			if (this.isLive == true && enemybullet.isLive == true
					&& this.getmyPlane().intersects(enemybullet.getenemyBullet())) {
				gs.blood-=40;
				if(gs.blood<=0) {
				this.isLive = false;
				}
				enemybullet.isLive = false;
			}
		}
	}

}

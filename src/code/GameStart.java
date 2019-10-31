package code;

import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class GameStart extends Frame {
	
//toolkit系统工具
	Toolkit tool=Toolkit.getDefaultToolkit(); //实例化工具对象
	//类路径的方式调用图片
	Image bgIMG=tool.getImage(GameStart.class.getResource("/imges/map01.jpg"));
	Image myPlaneIMG=tool.getImage(GameStart.class.getResource("/imges/cxk.png"));
	Image myBulletIMG=tool.getImage(GameStart.class.getResource("/imges/lq.png"));
	Image enemyPlaneIMG=tool.getImage(GameStart.class.getResource("/imges/a-05.png"));
	Image enemyBulletIMG=tool.getImage(GameStart.class.getResource("/imges/bullet-04-c.png"));
	Image overIMG=tool.getImage(GameStart.class.getResource("/imges/over3.png"));
	Image bombIMG=tool.getImage(GameStart.class.getResource("/imges/bomb5.png"));
	
	
	//背景的格式
	Backgroud Back =new Backgroud(0, 0, 500, 700, this);
	//飞机的格式
	MyPlane myPlane =new MyPlane(180, 550, 120, 80, true, this);
	//子弹篮球的集合
	ArrayList<MyBullet>myBulletList=new ArrayList<MyBullet>();
	//敌方飞机的集合
	ArrayList<EnemyPlane> enemyList=new ArrayList<EnemyPlane>();
	//敌机子弹
	ArrayList<EnemyBullet> enemyBulletList=new ArrayList<EnemyBullet>();
	//爆炸
	Bomb bomb=null;
	
	//游戏界面初始化
	public void init() {
		this.setTitle("机你太美");
		this.setSize(500, 700);
		this.setLocationRelativeTo(null); //居中
		this.setVisible(true);
		this.setResizable(false); //禁用最大化
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);//退出按钮
			}
		});
		
		new Mythread().start(); //开启线程
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				myPlane.movePlane(e); //调用我方飞机的移动方法
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				myPlane.stopPlane(e); //停止飞机移动
			}
		});
		
		
	}
	
	
	
	int time=0, etime=0;
	Random r=new Random();
	 //内部类,实现线程
	class Mythread extends Thread{
		@Override
		
		public void run() {
			while(true) {
				try {
					Thread.sleep(50);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint(); //重复调用
				//自动发射篮球
				time++;
	     		if(time%5==0) {
					MyBullet mybullet=new MyBullet(myPlane.x+50, myPlane.y, 50, 50,GameStart.this, true);
					//添加子弹
					//myBulletList.add(mybullet);
					time=0;
				}
				
				etime++;
				if(etime%10==0) {
				//敌机随机出现
				EnemyPlane enemy= new EnemyPlane(r.nextInt(460), 0, 50, 50, true, GameStart.this);
				enemyList.add(enemy);
				etime=0;
				
				}
				
			}
		}
	}
	
	
	Image temp=null;
	//双缓冲技术解决闪屏
	@Override
	public void update(Graphics g) {
		if(temp==null) {
			temp=this.createImage(500, 700);
		}
		Graphics tempG=temp.getGraphics(); //把成像的画笔传到缓冲区
		g.drawImage(temp, 0, 0,500,700, this); //成像
		paint(tempG); //交替成像paint 
	}
	
	int score=0;//游戏得分
	int blood=120;//血量
	int overY=700;//over
	//系统内置绘图的方法
	@Override
	public void paint(Graphics g) {
		//画背景
		Back.drawBG(g);
		myPlane.drawPlane(g);
		myPlane.isIntersect(enemyBulletList);//判断是否相交
		
		for(int i=0;i<myBulletList.size();i++) {
			//从集合里面拿出对象
			MyBullet mybullet =myBulletList.get(i);
			mybullet.drawMyBullet(g); //画出子弹
			mybullet.isIntersect(enemyList);//判断是否相交
			if(mybullet.y<-10)
			{
				myBulletList.remove(mybullet); //子弹消失
			}
			
			if(mybullet.isLive==false) {
				myBulletList.remove(mybullet);//子弹击中后消失
			}
			
			
		}
		//敌机出现
		for(int i=0;i<enemyList.size();i++) {
			EnemyPlane enemy=enemyList.get(i);
			enemy.drawEnemyPlane(g);
			if(enemy.y>700) {
				enemyList.remove(enemy);
			}
		}
		
		//敌机子弹循环输出
		for(int i=0;i<enemyBulletList.size();i++) {
			EnemyBullet enemybullet=enemyBulletList.get(i);
			enemybullet.drawenemybullet(g);
			if(enemybullet.y>700) {
				enemyList.remove(enemybullet);
			}
		}
		//设置字体颜色
		g.setColor(Color.white);
		g.setFont(new Font("宋体", Font.LAYOUT_LEFT_TO_RIGHT, 16));
		g.drawString("游戏得分："+score,5, 50);
		//血条
		g.drawImage(myPlaneIMG, 320, 25, 64, 40, this);
		g.setColor(Color.red);
		g.drawRect(370, 43, 120, 15);//空心血条框
		g.fillRect(370, 43, blood, 15);//实心填充
		
		//游戏结束
		
		if(!myPlane.isLive) {
			if(overY>280) {
			g.drawImage(overIMG, 90, overY, 320, 170, this);
			overY-=15;
			}else {
			g.drawImage(overIMG, 90, overY, 320, 170, this);
		}
		}
		
		//画出爆炸
		if(bomb!=null) {
			bomb.drawBomb(g);
		}
		bomb=null;
	}
	
	public static void main(String[] args) {
		GameStart g=new GameStart();
		g.init();

	}

}

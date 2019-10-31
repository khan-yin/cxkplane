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
	
//toolkitϵͳ����
	Toolkit tool=Toolkit.getDefaultToolkit(); //ʵ�������߶���
	//��·���ķ�ʽ����ͼƬ
	Image bgIMG=tool.getImage(GameStart.class.getResource("/imges/map01.jpg"));
	Image myPlaneIMG=tool.getImage(GameStart.class.getResource("/imges/cxk.png"));
	Image myBulletIMG=tool.getImage(GameStart.class.getResource("/imges/lq.png"));
	Image enemyPlaneIMG=tool.getImage(GameStart.class.getResource("/imges/a-05.png"));
	Image enemyBulletIMG=tool.getImage(GameStart.class.getResource("/imges/bullet-04-c.png"));
	Image overIMG=tool.getImage(GameStart.class.getResource("/imges/over3.png"));
	Image bombIMG=tool.getImage(GameStart.class.getResource("/imges/bomb5.png"));
	
	
	//�����ĸ�ʽ
	Backgroud Back =new Backgroud(0, 0, 500, 700, this);
	//�ɻ��ĸ�ʽ
	MyPlane myPlane =new MyPlane(180, 550, 120, 80, true, this);
	//�ӵ�����ļ���
	ArrayList<MyBullet>myBulletList=new ArrayList<MyBullet>();
	//�з��ɻ��ļ���
	ArrayList<EnemyPlane> enemyList=new ArrayList<EnemyPlane>();
	//�л��ӵ�
	ArrayList<EnemyBullet> enemyBulletList=new ArrayList<EnemyBullet>();
	//��ը
	Bomb bomb=null;
	
	//��Ϸ�����ʼ��
	public void init() {
		this.setTitle("����̫��");
		this.setSize(500, 700);
		this.setLocationRelativeTo(null); //����
		this.setVisible(true);
		this.setResizable(false); //�������
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);//�˳���ť
			}
		});
		
		new Mythread().start(); //�����߳�
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				myPlane.movePlane(e); //�����ҷ��ɻ����ƶ�����
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				myPlane.stopPlane(e); //ֹͣ�ɻ��ƶ�
			}
		});
		
		
	}
	
	
	
	int time=0, etime=0;
	Random r=new Random();
	 //�ڲ���,ʵ���߳�
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
				repaint(); //�ظ�����
				//�Զ���������
				time++;
	     		if(time%5==0) {
					MyBullet mybullet=new MyBullet(myPlane.x+50, myPlane.y, 50, 50,GameStart.this, true);
					//����ӵ�
					//myBulletList.add(mybullet);
					time=0;
				}
				
				etime++;
				if(etime%10==0) {
				//�л��������
				EnemyPlane enemy= new EnemyPlane(r.nextInt(460), 0, 50, 50, true, GameStart.this);
				enemyList.add(enemy);
				etime=0;
				
				}
				
			}
		}
	}
	
	
	Image temp=null;
	//˫���弼���������
	@Override
	public void update(Graphics g) {
		if(temp==null) {
			temp=this.createImage(500, 700);
		}
		Graphics tempG=temp.getGraphics(); //�ѳ���Ļ��ʴ���������
		g.drawImage(temp, 0, 0,500,700, this); //����
		paint(tempG); //�������paint 
	}
	
	int score=0;//��Ϸ�÷�
	int blood=120;//Ѫ��
	int overY=700;//over
	//ϵͳ���û�ͼ�ķ���
	@Override
	public void paint(Graphics g) {
		//������
		Back.drawBG(g);
		myPlane.drawPlane(g);
		myPlane.isIntersect(enemyBulletList);//�ж��Ƿ��ཻ
		
		for(int i=0;i<myBulletList.size();i++) {
			//�Ӽ��������ó�����
			MyBullet mybullet =myBulletList.get(i);
			mybullet.drawMyBullet(g); //�����ӵ�
			mybullet.isIntersect(enemyList);//�ж��Ƿ��ཻ
			if(mybullet.y<-10)
			{
				myBulletList.remove(mybullet); //�ӵ���ʧ
			}
			
			if(mybullet.isLive==false) {
				myBulletList.remove(mybullet);//�ӵ����к���ʧ
			}
			
			
		}
		//�л�����
		for(int i=0;i<enemyList.size();i++) {
			EnemyPlane enemy=enemyList.get(i);
			enemy.drawEnemyPlane(g);
			if(enemy.y>700) {
				enemyList.remove(enemy);
			}
		}
		
		//�л��ӵ�ѭ�����
		for(int i=0;i<enemyBulletList.size();i++) {
			EnemyBullet enemybullet=enemyBulletList.get(i);
			enemybullet.drawenemybullet(g);
			if(enemybullet.y>700) {
				enemyList.remove(enemybullet);
			}
		}
		//����������ɫ
		g.setColor(Color.white);
		g.setFont(new Font("����", Font.LAYOUT_LEFT_TO_RIGHT, 16));
		g.drawString("��Ϸ�÷֣�"+score,5, 50);
		//Ѫ��
		g.drawImage(myPlaneIMG, 320, 25, 64, 40, this);
		g.setColor(Color.red);
		g.drawRect(370, 43, 120, 15);//����Ѫ����
		g.fillRect(370, 43, blood, 15);//ʵ�����
		
		//��Ϸ����
		
		if(!myPlane.isLive) {
			if(overY>280) {
			g.drawImage(overIMG, 90, overY, 320, 170, this);
			overY-=15;
			}else {
			g.drawImage(overIMG, 90, overY, 320, 170, this);
		}
		}
		
		//������ը
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

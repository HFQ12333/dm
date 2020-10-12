package com.rjxy.hujie.controler;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.rjxy.hujie.bean.Bullet;
import com.rjxy.hujie.bean.EnemyPlane;
import com.rjxy.hujie.bean.Hero;
import com.v2.memento.Caretaker;
import com.v2.memento.Memento;
import com.v2.memento.Originator;
import com.v3.state.Context;

public class MyJPanel extends JPanel implements Runnable,MouseMotionListener {
	//private static final long serialVersionUID = 5179918663894372869L;

	//����ģʽ��ʹ�ã�ͨ������.get��������ȡΨһʵ��
	private Hero hero =  Hero.getInstance();

	private int bgX;
	private int bgY;
	//�л�����
	public ArrayList<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	private int epNum = 10;//�л�����
	
	private Image bgImg = null;
	private Image goImg = new ImageIcon("img/GameOver.jpg").getImage();
	// database 
	private DB_Operater db_Operater;
	// user name
	private String name;
	// game time,score
	private int curTime, lastTime, grade;
	///////////////////////
	// state ״̬ģʽ�����ò�ͬ�ؿ�
	public Context context=new Context();
	// runѭ����ʱ��Ƭ
	public int count = 0;
	
	public MyJPanel(Dimension dim, int grade, String name) {
		
		lastTime = (int)System.currentTimeMillis();
		// database
		this.name = name;
		this.grade = grade;
		db_Operater = new DB_Operater();
		
		
		//��ʼ����Ļ�Ŀ�͸�
		bgX = dim.width;
		bgY = dim.height;
		
		//��ʼ��ս��
		hero.setR(57);
		hero.setX((bgX - hero.getR() * 2) / 2);
		hero.setY(bgY - 3 * hero.getR());
		hero.setAllBlood(150);
		hero.setBlood(150);
		// init the blood of plane in db
		db_Operater.update(name, "150", "blood");
		// ������Ϸ���¼Ʒ֡���Ϸ�������Ƚ�ԭ������������󣬱����³ɼ�
		hero.setScore(0);
		
		//��ʼ���л�, 10���ն���
		for (int i = 0; i < epNum; i++) {
			EnemyPlane ep = new EnemyPlane();
			context.enemyUpdate(ep, bgX, bgY, count);
			enemyPlanes.add(ep);
		}
		
		//��ʼ������
		int bgNum = (int) (Math.random() * 5) + 1;
		bgImg = new ImageIcon("img/bg2.jpg").getImage();
		bgImg = new ImageIcon("img/bg" + bgNum + ".jpg").getImage();
		//System.out.println("img/bg" + bgNum + ".jpg");  //  img/bg3.jpg                
		//����������
		this.addMouseMotionListener(this);
		System.out.println("��幹�����");
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		//���ñ���ɫ
//		this.setBackground(Color.BLACK);
		g.drawImage(bgImg, 0, 0, bgX, bgY, null);
		//���Ƶл�
		
		
		
		for (int i = 0; i < enemyPlanes.size(); i++) {
			enemyPlanes.get(i).paint(g);
		}		
		//�����ӵ�
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bu = bullets.get(i);
			bu.paint(g);
		}
		//����ս��
		hero.paint(g);
		//�ؿ�
		g.drawString("��" + context.scenenum +"��", 820, 50);
		
		//GAMEOVER
		if (hero.getBlood() <= 0) {
			// update the data  
			int grade = hero.getScore();
			int level = hero.getScore()/300; 
			
			curTime = (int)System.currentTimeMillis();
			int time = (curTime - lastTime)/1000 ;//��һ�ε�ʱ��
			 
			
			/////////// ����¼�ı�������������ݱ��浽���ݿ� ////////////////////
			Originator originator = new Originator();
	        Caretaker caretaker = new Caretaker();
	        Memento m=originator.creatMementoObject(name, grade, level, time);
	        caretaker.saveMemento(m);
			 ////////////////////////////////
	        
			/*g.setFont(new Font("����", Font.BOLD, 50));
			g.drawString("GAME OVER", (bgX - 450) / 2, (bgY - 50) / 2);*/
			g.drawImage(goImg, 0, 0, bgX, bgY, null);
			//
			g.setFont(new Font("����", Font.BOLD, 20));
			g.drawString("��߷֣�" + m.getMaxgrade(), (bgX - 100) / 2, bgY - 200);			
		}
	}

	@Override
	public void run() {
				
		////////////////////////
		while (true) {
			//״̬ģʽ���л��ؿ�
			context.check(count);
			
			if (hero.getBlood() > 0) {
				//��ʼ���ӵ�
				if (count % 10 == 0) {
					initBullet();
				}
				count++;
				
				//�õл�����
				for (int i = 0; i < enemyPlanes.size(); i++) {
					EnemyPlane ep = enemyPlanes.get(i);
					if (ep.getY() >= bgY) {
						//״̬ģʽ�����õ�ǰ�ؿ��ĵл�����
						context.enemyUpdate(ep, bgX, bgY,count);
					} else {
						ep.setY(ep.getY() + ep.getSpeed());
					}
					//�жϵл��Ƿ���ս����ײ
					boolean b = isHeat(ep, hero);
					if (b == true) {
						//״̬ģʽ�����õ�ǰ�ؿ��ĵл�����
						context.enemyUpdate(ep, bgX, bgY,count);
						// get blood form db
						int curBlood = db_Operater.get(name, "blood");
						hero.setBlood(curBlood-hero.getAllBlood()/5);
						db_Operater.update(name, hero.getBlood()+"", "blood");
						
						// ����,����ը������						
						if(ep.getName().equals("e15"))
							hero.setScore( hero.getScore() - Context.ScoreReduce);
						
					}
				}
				
				//�ӵ��ɣ��ƶ�
				for (int i = 0; i < bullets.size(); i++) {
					Bullet bu = bullets.get(i);
					if (bu.getY() <= 0 - bu.getR()) {
						bullets.remove(i);
					} else {
						bu.setY(bu.getY() - 10);
					}					
				}
				//�ӵ���:��ײ���
				for (int i = 0; i < bullets.size(); i++) {
					Bullet bu = bullets.get(i);
					for (int j = 0; j < enemyPlanes.size(); j++) {
						EnemyPlane ep = enemyPlanes.get(j);
						boolean b = isHeat(bu,ep);
						//�ж��ӵ���л��Ƿ���ײ
						if (b == true && i<bullets.size() && ep!=null) {
							bullets.remove(i);
							hero.setScore(hero.getScore() + 10);
						 
							//״̬ģʽ�����õ�ǰ�ؿ��ĵл�����
							context.enemyUpdate(ep, bgX, bgY,count);							
						}
					}
				}
				repaint();
			}
			
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//�жϵл��Ƿ����ӵ���ײ
	private boolean isHeat(Bullet bu, EnemyPlane ep) {
		if ((bu.getX() - ep.getX()) * (bu.getX() - ep.getX()) + 
				(bu.getY() - ep.getY()) * (bu.getY() - ep.getY()) <=
				(bu.getR() + ep.getR()) * (bu.getR() + ep.getR())) {
			return true;
		} else {
			return false;
		}
	}

	//�жϵл��Ƿ���ս����ײ
	private boolean isHeat(EnemyPlane ep, Hero hero) {
		if ((ep.getX() - hero.getX()) * (ep.getX() - hero.getX()) + 
				(ep.getY() - hero.getY()) * (ep.getY() - hero.getY()) <=
				(ep.getR() + hero.getR()) * (ep.getR() + hero.getR())) {
			return true;
		} else {
			return false;
		}
	}

	private void initBullet() {
		Bullet bu = new Bullet();
		bu.setX(hero.getX());
		bu.setY(hero.getY());
		bu.setR(50);
		bullets.add(bu);
	}

	//��갴�²��ƶ�ʱ����
	@Override
	public void mouseDragged(MouseEvent e) {
		if (hero.getBlood() > 0) {
			hero.setX(e.getX());
			hero.setY(e.getY());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	/////////  Ϊ������ӵ���ģʽ���������Բ��ò������ݣ�ֱ�ӻ�ȡ����public����
	// ����ģʽ��д
	static private MyJPanel uniqueInstance;
	public static void setInstance(MyJPanel m) {
		uniqueInstance=m;
	}
	public static MyJPanel getInstance() {
		return uniqueInstance;
	}
}








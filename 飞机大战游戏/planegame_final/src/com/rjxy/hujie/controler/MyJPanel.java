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

public class MyJPanel extends JPanel implements Runnable,MouseMotionListener {
	//private static final long serialVersionUID = 5179918663894372869L;
	private Hero hero = new Hero();
	private int bgX;
	private int bgY;
	//�л�����
	private ArrayList<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private int epNum = 10;//�л�����
	private Image bgImg = null;
	private Image goImg = new ImageIcon("img/GameOver.jpg").getImage();
	// database 
	private DB_Operater db_Operater;
	private String name;
	private int curTime, lastTime, grade;

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
		hero.setScore(grade);
		
		//��ʼ���л�
		for (int i = 0; i < epNum; i++) {
			EnemyPlane ep = new EnemyPlane();
			ep.setX((int) (Math.random() * bgX));
			ep.setY((int) (Math.random() * bgY));
			ep.setR(25);
			ep.setSpeed((int) (Math.random() * 5) + 1);
			enemyPlanes.add(ep);
		}
		
		//��ʼ������
		int bgNum = (int) (Math.random() * 5) + 1;
		bgImg = new ImageIcon("img/bg2.jpg").getImage();
		bgImg = new ImageIcon("img/bg" + bgNum + ".jpg").getImage();
		//System.out.println("img/bg" + bgNum + ".jpg");  //  img/bg3.jpg                
		//����������
		this.addMouseMotionListener(this);

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
		
		//GAMEOVER
		if (hero.getBlood() <= 0) {
			// update the data  
			int grade = hero.getScore();
			int level = hero.getScore()/500; 
			
			curTime = (int)System.currentTimeMillis();
			int time = (curTime - lastTime)/1000 + db_Operater.get(name, "time");
			int temp = Math.max(grade, db_Operater.get(name, "maxgrade"));
			System.out.println("test:!!!"+temp);
			db_Operater.update(name, level, grade);
			db_Operater.update(name, time+"", "time");
			db_Operater.update(name, temp+"", "maxgrade");
			System.out.println("tetttt:"+db_Operater.get(name, "maxgrade"));
			/*g.setFont(new Font("����", Font.BOLD, 50));
			g.drawString("GAME OVER", (bgX - 450) / 2, (bgY - 50) / 2);*/
			g.drawImage(goImg, 0, 0, bgX, bgY, null);
			g.setFont(new Font("����", Font.BOLD, 20));
			
			int maxgrade = db_Operater.get(name, "maxgrade");  // get the maxgrade form the db
			
			g.drawString("��߷֣�" + maxgrade, (bgX - 100) / 2, bgY - 200);
			
		}
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			
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
						ep.setX((int) (Math.random() * bgX));
						ep.setY(0 - ep.getR());
						ep.setSpeed((int) (Math.random() * 5) + 1);
						ep.changeImg();
						enemyPlanes.remove(i);
						enemyPlanes.add(ep);
					} else {
						ep.setY(ep.getY() + ep.getSpeed());
					}
					//�жϵл��Ƿ���ս����ײ
					boolean b = isHeat(ep, hero);
					if (b == true) {
						ep.setX((int) (Math.random() * bgX));
						ep.setY(0 - ep.getR());
						ep.setSpeed((int) (Math.random() * 5) + 1);
						ep.changeImg();
						enemyPlanes.remove(i);
						enemyPlanes.add(ep);
//						hero.setBlood(hero.getBlood() - hero.getAllBlood() / 5);
						// get blood form db
						int curBlood = db_Operater.get(name, "blood");
						hero.setBlood(curBlood-hero.getAllBlood()/5);
						db_Operater.update(name, hero.getBlood()+"", "blood");
						
					}
				}
				
				//���ӵ���
				for (int i = 0; i < bullets.size(); i++) {
					Bullet bu = bullets.get(i);
					if (bu.getY() <= 0 - bu.getR()) {
						bullets.remove(i);
					} else {
						bu.setY(bu.getY() - 10);
					}
					for (int j = 0; j < enemyPlanes.size(); j++) {
						EnemyPlane ep = enemyPlanes.get(j);
						boolean b = isHeat(bu,ep);
						//�ж��ӵ���л��Ƿ���ײ
						if (b == true) {
							bullets.remove(i);
							ep.setX((int) (Math.random() * bgX));
							ep.setY(0 - ep.getR());
							ep.setSpeed((int) (Math.random() * 5) + 1);
							ep.changeImg();
							enemyPlanes.remove(j);
							enemyPlanes.add(ep);
							hero.setScore(hero.getScore() + 10);
						}
					}
				}
				repaint();
			}
			
			try {
				Thread.sleep(10);
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
}








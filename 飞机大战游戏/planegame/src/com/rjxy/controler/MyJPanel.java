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

	//单件模式的使用，通过类名.get函数，获取唯一实例
	private Hero hero =  Hero.getInstance();

	private int bgX;
	private int bgY;
	//敌机集合
	public ArrayList<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	private int epNum = 10;//敌机数量
	
	private Image bgImg = null;
	private Image goImg = new ImageIcon("img/GameOver.jpg").getImage();
	// database 
	private DB_Operater db_Operater;
	// user name
	private String name;
	// game time,score
	private int curTime, lastTime, grade;
	///////////////////////
	// state 状态模式，设置不同关卡
	public Context context=new Context();
	// run循环的时间片
	public int count = 0;
	
	public MyJPanel(Dimension dim, int grade, String name) {
		
		lastTime = (int)System.currentTimeMillis();
		// database
		this.name = name;
		this.grade = grade;
		db_Operater = new DB_Operater();
		
		
		//初始化屏幕的宽和高
		bgX = dim.width;
		bgY = dim.height;
		
		//初始化战机
		hero.setR(57);
		hero.setX((bgX - hero.getR() * 2) / 2);
		hero.setY(bgY - 3 * hero.getR());
		hero.setAllBlood(150);
		hero.setBlood(150);
		// init the blood of plane in db
		db_Operater.update(name, "150", "blood");
		// 本次游戏重新计分。游戏结束，比较原分数，如果更大，保存新成绩
		hero.setScore(0);
		
		//初始化敌机, 10个空对象
		for (int i = 0; i < epNum; i++) {
			EnemyPlane ep = new EnemyPlane();
			context.enemyUpdate(ep, bgX, bgY, count);
			enemyPlanes.add(ep);
		}
		
		//初始化背景
		int bgNum = (int) (Math.random() * 5) + 1;
		bgImg = new ImageIcon("img/bg2.jpg").getImage();
		bgImg = new ImageIcon("img/bg" + bgNum + ".jpg").getImage();
		//System.out.println("img/bg" + bgNum + ".jpg");  //  img/bg3.jpg                
		//设置鼠标监听
		this.addMouseMotionListener(this);
		System.out.println("面板构造完成");
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		//设置背景色
//		this.setBackground(Color.BLACK);
		g.drawImage(bgImg, 0, 0, bgX, bgY, null);
		//绘制敌机
		
		
		
		for (int i = 0; i < enemyPlanes.size(); i++) {
			enemyPlanes.get(i).paint(g);
		}		
		//绘制子弹
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bu = bullets.get(i);
			bu.paint(g);
		}
		//绘制战机
		hero.paint(g);
		//关卡
		g.drawString("第" + context.scenenum +"关", 820, 50);
		
		//GAMEOVER
		if (hero.getBlood() <= 0) {
			// update the data  
			int grade = hero.getScore();
			int level = hero.getScore()/300; 
			
			curTime = (int)System.currentTimeMillis();
			int time = (curTime - lastTime)/1000 ;//这一次的时间
			 
			
			/////////// 备忘录的保存操作，将数据保存到数据库 ////////////////////
			Originator originator = new Originator();
	        Caretaker caretaker = new Caretaker();
	        Memento m=originator.creatMementoObject(name, grade, level, time);
	        caretaker.saveMemento(m);
			 ////////////////////////////////
	        
			/*g.setFont(new Font("宋体", Font.BOLD, 50));
			g.drawString("GAME OVER", (bgX - 450) / 2, (bgY - 50) / 2);*/
			g.drawImage(goImg, 0, 0, bgX, bgY, null);
			//
			g.setFont(new Font("宋体", Font.BOLD, 20));
			g.drawString("最高分：" + m.getMaxgrade(), (bgX - 100) / 2, bgY - 200);			
		}
	}

	@Override
	public void run() {
				
		////////////////////////
		while (true) {
			//状态模式，切换关卡
			context.check(count);
			
			if (hero.getBlood() > 0) {
				//初始化子弹
				if (count % 10 == 0) {
					initBullet();
				}
				count++;
				
				//让敌机下落
				for (int i = 0; i < enemyPlanes.size(); i++) {
					EnemyPlane ep = enemyPlanes.get(i);
					if (ep.getY() >= bgY) {
						//状态模式，设置当前关卡的敌机数据
						context.enemyUpdate(ep, bgX, bgY,count);
					} else {
						ep.setY(ep.getY() + ep.getSpeed());
					}
					//判断敌机是否与战机相撞
					boolean b = isHeat(ep, hero);
					if (b == true) {
						//状态模式，设置当前关卡的敌机数据
						context.enemyUpdate(ep, bgX, bgY,count);
						// get blood form db
						int curBlood = db_Operater.get(name, "blood");
						hero.setBlood(curBlood-hero.getAllBlood()/5);
						db_Operater.update(name, hero.getBlood()+"", "blood");
						
						// 掉分,碰到炸弹掉分						
						if(ep.getName().equals("e15"))
							hero.setScore( hero.getScore() - Context.ScoreReduce);
						
					}
				}
				
				//子弹飞：移动
				for (int i = 0; i < bullets.size(); i++) {
					Bullet bu = bullets.get(i);
					if (bu.getY() <= 0 - bu.getR()) {
						bullets.remove(i);
					} else {
						bu.setY(bu.getY() - 10);
					}					
				}
				//子弹飞:碰撞检测
				for (int i = 0; i < bullets.size(); i++) {
					Bullet bu = bullets.get(i);
					for (int j = 0; j < enemyPlanes.size(); j++) {
						EnemyPlane ep = enemyPlanes.get(j);
						boolean b = isHeat(bu,ep);
						//判断子弹与敌机是否相撞
						if (b == true && i<bullets.size() && ep!=null) {
							bullets.remove(i);
							hero.setScore(hero.getScore() + 10);
						 
							//状态模式，设置当前关卡的敌机数据
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
	
	//判断敌机是否与子弹相撞
	private boolean isHeat(Bullet bu, EnemyPlane ep) {
		if ((bu.getX() - ep.getX()) * (bu.getX() - ep.getX()) + 
				(bu.getY() - ep.getY()) * (bu.getY() - ep.getY()) <=
				(bu.getR() + ep.getR()) * (bu.getR() + ep.getR())) {
			return true;
		} else {
			return false;
		}
	}

	//判断敌机是否与战机相撞
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

	//鼠标按下并移动时调用
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
	
	/////////  为面板增加单件模式，这样可以不用参数传递，直接获取所有public数据
	// 单件模式改写
	static private MyJPanel uniqueInstance;
	public static void setInstance(MyJPanel m) {
		uniqueInstance=m;
	}
	public static MyJPanel getInstance() {
		return uniqueInstance;
	}
}








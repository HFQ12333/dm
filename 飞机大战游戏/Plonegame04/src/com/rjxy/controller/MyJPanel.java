package com.rjxy.controller;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.rjxy.bean.Bullet;
import com.rjxy.bean.EnemyPlane;
import com.rjxy.bean.Hero;
import com.rjxy.bean.User;
import com.rjxy.stateMode.Context;

public class MyJPanel extends JPanel implements Runnable,MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	//private static final long serialVersionUID = 5179918663894372869L;
	
	//创建状态模式实例
	Context context = new Context();
	
	
	
	//利用单件模式，调用战机唯一实例
	private Hero hero = Hero.getInstance();
	private int bgX;
	private int bgY;
	//敌机集合
	private ArrayList<EnemyPlane> enemyPlanes = new ArrayList<EnemyPlane>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private int epNum = 10;//敌机数量
	private Image bgImg = null;
	private Image goImg = new ImageIcon("img/GameOver.jpg").getImage();
	private long startTime,endTime; 
	
	public MyJPanel(Dimension dim) {
		//初始化屏幕的宽和高
		bgX = dim.width;
		bgY = dim.height;
		
		//初始化战机
		hero.setR(57);
//		hero.setX((bgX - hero.getR() * 2) / 2);
		hero.setX(bgX/ 2);
		hero.setY(bgY - 3 * hero.getR());
		hero.setAllBlood(150);
		hero.setBlood(150);
		hero.setScore(0);
		
		//初始化敌机
		for (int i = 0; i < epNum; i++) {
			EnemyPlane ep = new EnemyPlane();
			ep.setX((int) (Math.random() * bgX));
			ep.setY((int) (Math.random() * bgY));
			ep.setR(25);
			ep.setSpeed((int) (Math.random() * 5) + 1);
			enemyPlanes.add(ep);
		}
		
		//初始化背景
		int bgNum = (int) (Math.random() * 5) + 1;
		//bgImg = new ImageIcon("img/bg2.jpg").getImage();
		bgImg = new ImageIcon("img/bg" + bgNum + ".jpg").getImage();
		//System.out.println("img/bg" + bgNum + ".jpg");  //  img/bg3.jpg                
		//设置鼠标监听  设置键盘监听
		this.addMouseMotionListener(this);
		this.addKeyListener(new MyKeyListener());

	}
	
	public void paint(Graphics g) {
		super.paint(g);
		//设置背景色
//		this.setBackground(Color.BLACK);
		g.drawImage(bgImg, 0, 0, bgX, bgY, null);
		//绘制敌机
		for (int i = 0; i < enemyPlanes.size(); i++) {
			enemyPlanes.get(i).paint(g);
			g.drawString("第"+context.stateNum+"关", 800, 50);
		}		
		//绘制子弹
		for (int i = 0; i < bullets.size(); i++) {
			Bullet bu = bullets.get(i);
			bu.paint(g);
		}
		//绘制战机
		hero.paint(g);
		
		//GAMEOVER
		if (hero.getBlood() <= 0) {
			/*g.setFont(new Font("宋体", Font.BOLD, 50));
			g.drawString("GAME OVER", (bgX - 450) / 2, (bgY - 50) / 2);*/
			g.drawImage(goImg, 0, 0, bgX, bgY, null);
			//游戏结束数据存档
			String name = User .getInstance().getUsername();
			DB_Operator.getInstance().update(name, context.stateNum, "level");
			DB_Operator.getInstance().update(name, hero.getScore(), "grade");
			int time =(int)(endTime-startTime)/1000;
			time += DB_Operator.getInstance().get(name, "time");
			DB_Operator.getInstance().update(name, time, "time");
			int maxScroe = DB_Operator .getInstance().get(name, "maxgrade");
			maxScroe = (hero.getScore()>maxScroe?hero.getScore():maxScroe);
			DB_Operator .getInstance().update(name, maxScroe, "maxScore");
		
			
			/*int maxScore = 0;
			File file = new File("data/maxScore.txt");
			
			try {
				if(!file.exists()){
					//如果保存分数文件不存在，创建文件，创建输入流写入数据
					file.createNewFile();
					FileWriter writer = new FileWriter(file);
					BufferedWriter bw = new BufferedWriter(writer);
					bw.write(hero.getScore()+"");
					bw.flush();
					bw.close();
					maxScore = hero.getScore();
				} else {
					//创建输出流，读取文件
					InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
					BufferedReader br = new BufferedReader(reader);
					//String line = "";
					int score = 0;
					if (-1 !=br.read()) {
						//如果读到数据
						//score = Integer.parseInt(line);
						score=br.read();
						if(hero.getScore() > score){
							FileWriter writer = new FileWriter(file);
							BufferedWriter bw = new BufferedWriter(writer);
							bw.write(hero.getScore()+"");
							bw.flush();
							bw.close();
							maxScore = hero.getScore();
						} else {
							maxScore = score;
						}
					} else {
						//如果文件内容为空
						System.out.println("手动删除maxScore.txt文件重新运行");
					}
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			g.setFont(new Font("宋体", Font.BOLD, 20));
			g.drawString("最高分：" + maxScore, (bgX - 100) / 2, bgY - 100);
			
		*/	
		}
	}

	@Override
	public void run() {
		
		int count = 0; //时间片
		startTime = System.currentTimeMillis();
		while (true) {		
			context.change();
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
						/*enemyPlanes.remove(i);
						ep.setX((int) (Math.random() * bgX));
						ep.setY(0 - ep.getR());
						ep.setSpeed((int) (Math.random() * 5) + 1);
						ep.changeImg();
						enemyPlanes.add(ep);*/
						
						//状态模式更新敌机信息
						context.enemyUpdate(ep, bgX, bgY, count);
					} else {
						ep.setY(ep.getY() + ep.getSpeed());
					}
					//判断敌机是否与战机相撞
					boolean b = isHeat(ep, hero);
					if (b == true) {
						/*ep.setX((int) (Math.random() * bgX));
						ep.setY(0 - ep.getR());
						ep.setSpeed((int) (Math.random() * 5) + 1);
						ep.changeImg();
						enemyPlanes.remove(i);
						enemyPlanes.add(ep);*/
					  context.enemyUpdate(ep, bgX, bgY, count);
						hero.setBlood(hero.getBlood() - hero.getAllBlood() / 5);
					}
				}
				
				//让子弹飞
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
						//判断子弹与敌机是否相撞
						if (b == true) {
							bullets.remove(i);
							/*ep.setX((int) (Math.random() * bgX));
							ep.setY(0 - ep.getR());
							ep.setSpeed((int) (Math.random() * 5) + 1);
							ep.changeImg();
							enemyPlanes.remove(j);
							enemyPlanes.add(ep);*/
							context.enemyUpdate(ep, bgX, bgY, count);
							hero.setScore(hero.getScore() + 10);
						}
					}
				}
				repaint();
			}
			else{
			endTime = System.currentTimeMillis();
			break;
			}
			
			try {
				Thread.sleep(10);
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
			repaint();//自动调用当前容器的public void paint(Graphics g)方法
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	class MyKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			super.keyPressed(e);
			int code=e.getKeyCode();
			switch(code){
			case KeyEvent.VK_UP:
				hero.setY(hero.getY()-20);break;
			case KeyEvent.VK_LEFT:
				hero.setX(hero.getX()-20);break;
			case KeyEvent.VK_RIGHT:
				hero.setX(hero.getX()+20);break;
			case KeyEvent.VK_DOWN:
				hero.setY(hero.getY()+20);break;
			case KeyEvent.VK_Z://放大招
				
				break;
			default:
				break;
			}
			
		}
	}

}








package com.rjxy.bean;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 敌机类
 * @author teacher
 *
 */
public class EnemyPlane {
	private int x;
	private int y;
	private int r;
	private int speed;
	private Image epImg = null;
	private int epNum =0;
	
	public EnemyPlane() {
		epNum = (int) (Math.random() * 15) + 1;
		String ep = null;
		if (epNum < 10) {
			ep = "0" + epNum;
		} else {
			ep = "" + epNum;
		}
		epImg = new ImageIcon("img/ep" + ep + ".png").getImage();
	}
	
	public void changeImg() {
		epNum = (int) (Math.random() * 15) + 1;
		String ep = null;
		if (epNum < 10) {
			ep = "0" + epNum;
		} else {
			ep = "" + epNum;
		}
		epImg = new ImageIcon("img/ep" + ep + ".png").getImage();
	}
	//重载状态模式选择敌机
	
	public void changeImage(int imgID_lower,int imgID_upper) {
		int range = imgID_upper - imgID_lower;
		epNum=(int)(Math.random()*range)+imgID_lower;
		String epString=null;
		if(epNum<10){
			epString="0"+epNum;
		}
		else{
			epString=""+epNum;
		}
		epImg =new ImageIcon("src/img/ep"+epString+".png").getImage();
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	
	public void paint(Graphics g) {
		/*g.setColor(Color.BLUE);
		g.fillOval(x - r, y - r, r * 2, r * 2);*/
		g.drawImage(epImg, x - r, y - r, null);
	}
	
}







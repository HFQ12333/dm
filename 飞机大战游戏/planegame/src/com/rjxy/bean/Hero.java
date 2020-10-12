package com.rjxy.hujie.bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * ս����
 * @author teacher
 *
 */
public class Hero {
	
	
	private int x;
	private int y;
	private int r;
	private int AllBlood;
	private int blood;
	private int score;
	private Image heroImg = new ImageIcon("img/hero.png").getImage();
	
	// ����ģʽ
	static private Hero uniqueInstance;
	
	public static Hero getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Hero();//��uniqueInstance������ʱ�Ŵ���
		}
		return uniqueInstance;
	}
	//////////////////////////////
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getAllBlood() {
		return AllBlood;
	}
	public void setAllBlood(int allBlood) {
		AllBlood = allBlood;
	}
	public int getBlood() {
		return blood;
	}
	public void setBlood(int blood) {
		this.blood = blood;
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
		/*g.setColor(Color.YELLOW);
		g.fillOval(x - r, y - r, r * 2, r * 2);*/
		g.drawImage(heroImg, x - r, y - r, null);
		//������Ѫ��
		g.setColor(Color.ORANGE);
		g.fillRect(20, 20, AllBlood, 30);
		//����ʣ��Ѫ��
		g.setColor(Color.RED);
		g.fillRect(20, 20, blood, 30);
		g.setFont(new Font("����", Font.BOLD, 20));
		g.drawString("������" + score, 900, 50);
		
		//
		
	}
	
	
}













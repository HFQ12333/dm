package com.rjxy.hujie.bean;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * ×Óµ¯Àà
 * @author teacher
 *
 */
public class Bullet {
	private int x;
	private int y;
	private int r;
	private int time;
	private Image buImg = new ImageIcon("img/fire.png").getImage();
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
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
		g.drawImage(buImg, x - r, y - r, null);
	}
	
}







package com.rjxy.hujie.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.rjxy.hujie.controler.DB_Operater;
import com.rjxy.hujie.controler.MyJPanel;

public class Started{
	private static final long serialVersionUID = -3751944645387045807L;
	int level, grade;
	String name;
	DB_Operater db_Operater;
	int lastTime;
	public Started(int level, int grade, String name) {
		this.level = level;
		this.grade = grade;
		this.name = name;
		db_Operater = new DB_Operater();
		lastTime = (int) System.currentTimeMillis();
		//定义窗体对象
		JFrame frame = new JFrame("飞机大战");
		//获取屏幕分辨率
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//设置窗体为全屏

		
		frame.setBounds(0, 0, dim.width, dim.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyJPanel panel = new MyJPanel(dim, grade, name);
		frame.add(panel);
		
		//定义线程
		Thread thread = new Thread(panel);
		//启动线程
		thread.start();

		frame.setVisible(true);
		//设置窗体大小不可改变
		frame.setResizable(false);
	}
	
//	@Override
//	public void keyTyped(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyPressed(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void keyReleased(KeyEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
}

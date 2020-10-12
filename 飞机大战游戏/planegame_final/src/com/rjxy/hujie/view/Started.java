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
		//���崰�����
		JFrame frame = new JFrame("�ɻ���ս");
		//��ȡ��Ļ�ֱ���
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//���ô���Ϊȫ��

		
		frame.setBounds(0, 0, dim.width, dim.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyJPanel panel = new MyJPanel(dim, grade, name);
		frame.add(panel);
		
		//�����߳�
		Thread thread = new Thread(panel);
		//�����߳�
		thread.start();

		frame.setVisible(true);
		//���ô����С���ɸı�
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

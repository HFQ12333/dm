package com.rjxy.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.rjxy.controller.MyJPanel;

public class Started extends JFrame {
	private static final long serialVersionUID = -3751944645387045807L;

	public Started() {
		//���崰�����
		JFrame frame = new JFrame("�ɻ���ս");
		//��ȡ��Ļ�ֱ���
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//���ô���Ϊȫ��
//		frame.setSize(dim);
//		frame.setSize(dim.width, dim.height);
		frame.setBounds(0, 0, dim.width, dim.height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyJPanel panel = new MyJPanel(dim);
		frame.add(panel);
		panel.setFocusable(true);

		
		//�����߳�
		Thread thread = new Thread(panel);
		//�����߳�
		thread.start();

		frame.setVisible(true);
		//���ô����С���ɸı�
		frame.setResizable(false);		
	}
}

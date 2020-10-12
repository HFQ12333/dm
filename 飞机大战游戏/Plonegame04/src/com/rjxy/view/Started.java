package com.rjxy.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.rjxy.controller.MyJPanel;

public class Started extends JFrame {
	private static final long serialVersionUID = -3751944645387045807L;

	public Started() {
		//定义窗体对象
		JFrame frame = new JFrame("飞机大战");
		//获取屏幕分辨率
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//设置窗体为全屏
//		frame.setSize(dim);
//		frame.setSize(dim.width, dim.height);
		frame.setBounds(0, 0, dim.width, dim.height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MyJPanel panel = new MyJPanel(dim);
		frame.add(panel);
		panel.setFocusable(true);

		
		//定义线程
		Thread thread = new Thread(panel);
		//启动线程
		thread.start();

		frame.setVisible(true);
		//设置窗体大小不可改变
		frame.setResizable(false);		
	}
}

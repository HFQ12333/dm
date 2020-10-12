package com.rjxy.hujie.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.rjxy.hujie.controler.DB_Operater;

public class Start extends JFrame implements ActionListener {
	private static final long serialVersionUID = 293145988993785462L;
	private JButton button_start = new JButton();
	private JButton button_end = new JButton();
	private int level, grade, time;
	private String name;
	public Start(int level, int grade, String name, int time) {
		this.level = level;
		this.grade = grade;
		this.name = name;
		this.time = time;
		DB_Operater db_Operater = new DB_Operater();
		JFrame frame = new JFrame("开始界面");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((dim.width - 300) / 2, (dim.height - 400) / 2, 400, 200);//居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button_start.setText("开始游戏");
		button_start.addActionListener(this);//为开始游戏按钮增加动作监听器
		button_end.setText("结束游戏");
		button_end.addActionListener(this);//为结束游戏按钮增加动作监听器
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		JTextField Level = new JTextField(20);  
		JTextField Grade = new JTextField(20);
		JTextField Time = new JTextField(20);
		
		Level.setEnabled(false);
		Grade.setEnabled(false);
		Time.setEnabled(false);
		
		Level.setText(level+"");
		Grade.setText(grade+"");
		Time.setText(time+"秒");
		
		panel1.add(button_start);
		panel1.add(button_end);
		panel2.add(new Label("等级："));  
        panel2.add(Level);  
        panel3.add(new Label("成绩："));  
        panel3.add(Grade);
        panel4.add(new Label("累计在线："));
        panel4.add(Time);
        panel5.add(panel2,BorderLayout.NORTH);
        panel5.add(panel3,BorderLayout.CENTER);
        panel5.add(panel4,BorderLayout.SOUTH);
        
		frame.add(panel1, BorderLayout.SOUTH);
		frame.add(panel5, BorderLayout.CENTER);
//		frame.add(panel3, BorderLayout.CENTER);
//		frame.add(panel4, BorderLayout.SOUTH);
		frame.setResizable(false);//设置窗体大小不可改变
		frame.setVisible(true);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button_start) {
			new Started(level, grade, name);
		} 
		if (e.getSource() == button_end) {
			System.exit(0);
		}
	}
}







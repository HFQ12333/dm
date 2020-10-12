package com.rjxy.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.jws.soap.SOAPBinding.Use;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rjxy.bean.User;
import com.rjxy.controller.DB_Operator;

public class Start extends JFrame  {
	DB_Operator db_Operator = new DB_Operator();
	private static final long serialVersionUID = 293145988993785462L;
	private JButton button_start = new JButton();
	private JButton button_end = new JButton();
	
	/*public static void main(String[] args) {
		new Start();
	}*/
	public Start(String name) {
		JFrame frame = new JFrame("开始界面");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((dim.width - 300) / 2, (dim.height - 400) / 2, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button_start.setText("开始游戏");
		button_start.addActionListener(new MyActionListener());
		button_end.setText("结束游戏");
		button_end.addActionListener(new MyActionListener());
		JPanel panel = new JPanel();
		panel.add(button_start);
		panel.add(button_end);
		
		//添加用户历史记录包括用户名，等级，上一次得分，累计游戏时间，最高得分
		JTextField tf_name = new JTextField(20);
		JTextField tf_level = new JTextField(20);
		JTextField tf_time = new JTextField(10);
		JTextField tf_maxgeade = new JTextField(10);
		
		
		String username = User.getInstance().getUsername();
		tf_name.setText(name);
		tf_level.setText(db_Operator.get(name, "level")+"");
		tf_time.setText(db_Operator.get(name, "time")+"");
		tf_maxgeade.setText(db_Operator.get(name, "maxgeade")+"");
		tf_name.setEditable(false);
		tf_level.setEditable(false);
		tf_time.setEditable(false);
		tf_maxgeade.setEditable(false);
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		panel1.add(new Label("用户名"));
		panel1.add(tf_name);
		panel2.add(new Label("等级"));
		panel2.add(tf_level);
		panel3.add(new Label("累计时间"));
		panel3.add(tf_time);
		panel4.add(new Label("最高得分"));
		panel4.add(tf_maxgeade);
		
		frame.setLayout(new GridLayout(5,1));
		frame.add(panel1);
		frame.add(panel2);
		frame.add(panel3);
		frame.add(panel4);
		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	
	private class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == button_start) {
			new Started();
		} 
		if (e.getSource() == button_end) {
			System.exit(0);
		}
		
		}
	}
}


/*public class Start extends JFrame implements ActionListener {
	private static final long serialVersionUID = 293145988993785462L;
	private JButton button_start = new JButton();
	private JButton button_end = new JButton();
	
	public static void main(String[] args) {
		new Start();
	}
	public Start() {
		JFrame frame = new JFrame("开始界面");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((dim.width - 300) / 2, (dim.height - 400) / 2, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button_start.setText("开始游戏");
		button_start.addActionListener(this);
		button_end.setText("结束游戏");
		button_end.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(button_start);
		panel.add(button_end);
		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button_start) {
			new Started();
		} 
		if (e.getSource() == button_end) {
			System.exit(0);
		}
	}
}
*/








package com.rjxy.hujie.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rjxy.hujie.controler.DB_Operater;
import com.v2.memento.Caretaker;
import com.v2.memento.Originator;

import java.awt.event.*;
import java.util.logging.Level;

/**
 * 作者：zg 功能： 1.账号注册 2.游戏登陆 用户校验 时间： 2018-1-1
 */
public class Jm_start extends JFrame implements MouseListener {

	 
	// 按钮
	private JButton bt_signup;
	private JButton bt_login;
	private JTextField tf_name;
	private JTextField tf_pass;
	// database
	DB_Operater db_Operater;
	Jm_start() {
		// database
		db_Operater = new DB_Operater();

		this.setTitle("飞机大战");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// 文本框
		JPanel p_text1 = new JPanel();
		JPanel p_text2 = new JPanel();
		tf_name = new JTextField(20);
		tf_pass = new JTextField(20);
		p_text1.add(new Label("用户名"));
		p_text2.add(new Label("密 码"));
		p_text1.add(tf_name);
		p_text2.add(tf_pass);
		
		// 按钮栏
		JPanel p_bt = new JPanel();
		bt_signup = new JButton("注册账号");
		bt_login = new JButton("登陆游戏");
		p_bt.add(bt_signup);
		p_bt.add(bt_login);

		// 监听器
		bt_signup.addMouseListener(this);
		bt_login.addMouseListener(this);
		
		this.add(p_text1, BorderLayout.NORTH);
		this.add(p_text2, BorderLayout.CENTER);
		this.add(p_bt, BorderLayout.SOUTH);
		this.setBounds((dim.width - 400) / 2, (dim.height - 300) / 2, 350, 150);// 界面居中
		this.setResizable(false);// 大小不可改变
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// 主函数
	public static void main(String[] args) {
		new Jm_start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		String name = tf_name.getText().toString().trim();
		String pass = tf_pass.getText().toString().trim();
		
		if (e.getSource() == bt_signup) {
			if (name.equals("")||name.length()==0||pass.equals("")||pass.length()==0) {
				// 输出错误信息
				JOptionPane.showMessageDialog(null, "请输入账号和密码!");
			} else {
				if (db_Operater.exist(name)) {
					JOptionPane.showMessageDialog(null, "该账号已存在!请重新输入");
				} else {
					db_Operater.add(name, pass);
					JOptionPane.showMessageDialog(null, "注册成功，请登陆！");
				}
			}
		} else if (e.getSource() == bt_login) { 
			if (name.equals("")||name.length()==0||pass.equals("")||pass.length()==0) {
				// 输出错误信息
				JOptionPane.showMessageDialog(null, "请输入账号和密码!");
			} else {
				if( db_Operater.check(name, pass) ) {  //登陆成功
					// 跳转主界面
					this.setVisible(false);
					
					/////////// 备忘录的恢复操作，读取数据库 ，恢复数据 ////////////////////
					Originator originator = new Originator();
			        Caretaker caretaker = new Caretaker();
					 originator.restoreMemento(caretaker.retrieveMemento( name));
					 ////////////////////////////////
					 
					int level = originator.m.getLevel();
					int grade =  originator.m.getGrade();
					int time = originator.m.getTime();
					new Start(level, grade, name, time);
				} else {
					// 不跳转 输出错误信息
					JOptionPane.showMessageDialog(null, "请正确的账号和密码!");
				}
			}
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
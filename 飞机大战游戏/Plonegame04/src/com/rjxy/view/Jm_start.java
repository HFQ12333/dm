package com.rjxy.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rjxy.bean.User;
import com.rjxy.controller.DB_Operator;
import com.rjxy.util.JDBCTools;

public class Jm_start extends JFrame implements MouseListener{
	DB_Operator db=new DB_Operator();
	JButton bt_signup;
	JButton bt_login;
	JTextField tf_name;
	JTextField tf_pass;
	JLabel la_name;
	JLabel la_pass;
	
	public Jm_start(){
		tf_name=new JTextField(20);
		tf_pass=new JTextField(20);
		bt_login=new JButton("登录游戏");
		bt_signup=new JButton("注册账号");
		la_name=new JLabel("用户名");
		la_pass=new JLabel("密   码");
		
		//将组件分别放到三个容器里
		JPanel p_text1=new JPanel();
		p_text1.add(la_name);
		p_text1.add(tf_name);
		
		JPanel p_text2=new JPanel();
		p_text2.add(la_pass);
		p_text2.add(tf_pass);
		
		JPanel p_bt=new JPanel();
		p_bt.add(bt_signup);
		p_bt.add(bt_login);
		
		bt_login.addMouseListener(this);
		bt_signup.addMouseListener(this);
		
		this.add(p_text1, BorderLayout.NORTH);
		this.add(p_text2,BorderLayout.CENTER);
		this.add(p_bt,BorderLayout.SOUTH);
		
		
		//对窗体设置
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//获取屏幕的宽高
		this.setBounds((dim.width-300)/2, (dim.height-150)/2, 300, 150);//设置初始显示位置
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) throws Exception{
		new Jm_start();
		//测试JDBCTools
		Connection conn=JDBCTools.getConnection();
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==bt_signup){
			//实现注册功能
			String name=tf_name.getText();
			String pass=tf_pass.getText();
			//判空校验
			if(name.equals("") || name == null || pass.equals("") || pass == null){
				JOptionPane.showMessageDialog(null, "用户名或密码不能为空");
			}else{
				//先去数据库检查对应用户名是否存在exist（name）
				//不存在再添加，注册成功add（name，pass）
				if (db.exist(name)) {
					JOptionPane.showMessageDialog(null, "用户名已存在，请大侠重新赐名");
				}else {
					db.add(name, pass);
					JOptionPane.showMessageDialog(null, "注册成功，请登录");
				}
			}
			
		}else{
			if(e.getSource()==bt_login){
				//实现登录功能
				String name=tf_name.getText();
				String pass=tf_pass.getText();
				//判空校验
				if(name.equals("") || name == null || pass.equals("") || pass == null){
					JOptionPane.showMessageDialog(null, "老铁用户名或密码不能为空");
				}else{
					//去数据库检查对应用户名和密码相同记录是否存在check（name，pass）
					//存在则登录成功，不存在登录失败
					if (db.check(name, pass)) {
						//用户名密码正确，登录成功
						User.getInstance().setUsername(name);
						new Start(name);
						
					} else {
						JOptionPane.showMessageDialog(null, "大侠用户名或密码错误，请重新输入");
					}
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
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
		bt_login=new JButton("��¼��Ϸ");
		bt_signup=new JButton("ע���˺�");
		la_name=new JLabel("�û���");
		la_pass=new JLabel("��   ��");
		
		//������ֱ�ŵ�����������
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
		
		
		//�Դ�������
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();//��ȡ��Ļ�Ŀ��
		this.setBounds((dim.width-300)/2, (dim.height-150)/2, 300, 150);//���ó�ʼ��ʾλ��
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) throws Exception{
		new Jm_start();
		//����JDBCTools
		Connection conn=JDBCTools.getConnection();
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==bt_signup){
			//ʵ��ע�Ṧ��
			String name=tf_name.getText();
			String pass=tf_pass.getText();
			//�п�У��
			if(name.equals("") || name == null || pass.equals("") || pass == null){
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			}else{
				//��ȥ���ݿ����Ӧ�û����Ƿ����exist��name��
				//����������ӣ�ע��ɹ�add��name��pass��
				if (db.exist(name)) {
					JOptionPane.showMessageDialog(null, "�û����Ѵ��ڣ���������´���");
				}else {
					db.add(name, pass);
					JOptionPane.showMessageDialog(null, "ע��ɹ������¼");
				}
			}
			
		}else{
			if(e.getSource()==bt_login){
				//ʵ�ֵ�¼����
				String name=tf_name.getText();
				String pass=tf_pass.getText();
				//�п�У��
				if(name.equals("") || name == null || pass.equals("") || pass == null){
					JOptionPane.showMessageDialog(null, "�����û��������벻��Ϊ��");
				}else{
					//ȥ���ݿ����Ӧ�û�����������ͬ��¼�Ƿ����check��name��pass��
					//�������¼�ɹ��������ڵ�¼ʧ��
					if (db.check(name, pass)) {
						//�û���������ȷ����¼�ɹ�
						User.getInstance().setUsername(name);
						new Start(name);
						
					} else {
						JOptionPane.showMessageDialog(null, "�����û����������������������");
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
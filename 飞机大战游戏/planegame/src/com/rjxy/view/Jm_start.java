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
 * ���ߣ�zg ���ܣ� 1.�˺�ע�� 2.��Ϸ��½ �û�У�� ʱ�䣺 2018-1-1
 */
public class Jm_start extends JFrame implements MouseListener {

	 
	// ��ť
	private JButton bt_signup;
	private JButton bt_login;
	private JTextField tf_name;
	private JTextField tf_pass;
	// database
	DB_Operater db_Operater;
	Jm_start() {
		// database
		db_Operater = new DB_Operater();

		this.setTitle("�ɻ���ս");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// �ı���
		JPanel p_text1 = new JPanel();
		JPanel p_text2 = new JPanel();
		tf_name = new JTextField(20);
		tf_pass = new JTextField(20);
		p_text1.add(new Label("�û���"));
		p_text2.add(new Label("�� ��"));
		p_text1.add(tf_name);
		p_text2.add(tf_pass);
		
		// ��ť��
		JPanel p_bt = new JPanel();
		bt_signup = new JButton("ע���˺�");
		bt_login = new JButton("��½��Ϸ");
		p_bt.add(bt_signup);
		p_bt.add(bt_login);

		// ������
		bt_signup.addMouseListener(this);
		bt_login.addMouseListener(this);
		
		this.add(p_text1, BorderLayout.NORTH);
		this.add(p_text2, BorderLayout.CENTER);
		this.add(p_bt, BorderLayout.SOUTH);
		this.setBounds((dim.width - 400) / 2, (dim.height - 300) / 2, 350, 150);// �������
		this.setResizable(false);// ��С���ɸı�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	// ������
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
				// ���������Ϣ
				JOptionPane.showMessageDialog(null, "�������˺ź�����!");
			} else {
				if (db_Operater.exist(name)) {
					JOptionPane.showMessageDialog(null, "���˺��Ѵ���!����������");
				} else {
					db_Operater.add(name, pass);
					JOptionPane.showMessageDialog(null, "ע��ɹ������½��");
				}
			}
		} else if (e.getSource() == bt_login) { 
			if (name.equals("")||name.length()==0||pass.equals("")||pass.length()==0) {
				// ���������Ϣ
				JOptionPane.showMessageDialog(null, "�������˺ź�����!");
			} else {
				if( db_Operater.check(name, pass) ) {  //��½�ɹ�
					// ��ת������
					this.setVisible(false);
					
					/////////// ����¼�Ļָ���������ȡ���ݿ� ���ָ����� ////////////////////
					Originator originator = new Originator();
			        Caretaker caretaker = new Caretaker();
					 originator.restoreMemento(caretaker.retrieveMemento( name));
					 ////////////////////////////////
					 
					int level = originator.m.getLevel();
					int grade =  originator.m.getGrade();
					int time = originator.m.getTime();
					new Start(level, grade, name, time);
				} else {
					// ����ת ���������Ϣ
					JOptionPane.showMessageDialog(null, "����ȷ���˺ź�����!");
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
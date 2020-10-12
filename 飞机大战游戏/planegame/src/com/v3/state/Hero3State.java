package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;
import com.rjxy.hujie.controler.MyJPanel;

//��3�أ�����ʱ��Ƭ�������������򣬽�����ֵл�
//��3�أ������ɫ�ɻ� 08.png���Ҳ��ɫ�ɻ� 07.png
public class Hero3State implements HeroState{
	
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) {
		int sx;
		//ÿ����ʱ20ms, ÿ50��ʱ��Ƭ��1��
		//ÿ���ʱ��Ƭ600��ǰһ�������ɻ�����һ���Ҳ���ɻ�
		if(count % 600 < 300)
		{
			sx=(int) (Math.random() * bgX/2);
			ep.changeImg(8, 8);
		}
		else
		{
			sx=(int) (Math.random() * bgX/2)+ bgX/2;
			ep.changeImg(7, 7);
		}
			
		ep.setX(sx);
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 2) + 2);		
		
	}
}

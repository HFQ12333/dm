package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;

//��2�أ����λ��,���Ϸ���
public class Hero2State implements HeroState{
	
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) 
	{
		// �Ϸ��л����������м�1/2��������1/4û�ел�
		// ����λ�ã�����ʱ��Ƭ
		int sx=bgX/4;
		ep.setX((int) (Math.random() * (bgX/2)+sx));
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 2) + 2);		
		ep.changeImg(4,6);
	}
}

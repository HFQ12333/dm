package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;

//��һ�أ����λ��,���Ϸ���
public class Hero1State implements HeroState{
	
	//��һ�أ�����λ�ã���ʹ��ʱ��Ƭ
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) {
		ep.setX((int) (Math.random() * bgX));
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 2) + 2);
		//��1�أ�ͼƬ01.png -03.png
		ep.changeImg(1,3);
	}
	
}

package com.rjxy.stateMode;
/*
 * ���Ĺ�
 * ���ܣ��л�����ʱ��Ƭ���������γ��֣�ÿ��ʱ��Ƭ.300���ӿ�л��ٶ�
 * 
 * */




import com.rjxy.bean.EnemyPlane;

public class Hero4State implements HeroState {
	
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY, int count) {
		// TODO Auto-generated method stub
		if (count % 900<300) {//��1/3
	 		ep.setX((int)(int)(Math.random()*(bgX/3)));
	 		ep.changeImage(7,7);
	 	}else if (count %900 <600) {//��
	 		ep.setX((int)(int)(Math.random()*(bgX/3)) + (bgX*2/3));
	 	}else {//��1/3
	 		ep.setX((int)(int)(Math.random()*(bgX/2)) + (bgX));		
			}
				
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int)(Math.random()*3)+3);
		ep.changeImage(12,15);
	
	}
	}

package com.rjxy.stateMode;
/*
 * ������
 * ���õл�������֣�ǰ300ʱ��Ƭ������(ep07),��300�Ҳ����
 * @author:LSR
 * */
import com.rjxy.bean.EnemyPlane;

public class Hero3State implements HeroState {

	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY, int count) {
		// TODO Auto-generated method stub
		if (count % 600<300) {//��1/2
	 		ep.setX((int)(int)(Math.random()*(bgX/2)));
	 		ep.changeImage(7,7);
		} else {//��1/2
			ep.setX((int)((int)(Math.random()*(bgX/2)) +(bgX/4)));
		}
		
		
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int)(Math.random()*2)+2);
		ep.changeImage(8,8);
	} 
	
}


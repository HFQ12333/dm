package com.rjxy.stateMode;
import com.rjxy.bean.EnemyPlane; 
/*
 * ��һ��
 * @author LSR
 * ���ܣ����õл�����λ�ã�ʹ��С�ͷɻ���ep01-05��
 * */
public class Hero1State implements HeroState {

	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY, int count) {
		// TODO Auto-generated method stub
		ep.setX((int)(Math.random() * bgX));
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int)(Math.random()*2)+2);
		ep.changeImage(1,6);

	} 
	
}


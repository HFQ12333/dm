package com.rjxy.stateMode;
/*�м�1/2����
 * ���͵л�*/
import com.rjxy.bean.EnemyPlane;

public class Hero2State implements HeroState {
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY, int count) {
		// TODO Auto-generated method stub
		ep.setX((int)((int)Math.random()*(bgX/2)) +(bgX/4));
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int)(Math.random()*2)+2);
		ep.changeImage(6,9);

	} 
}
package com.rjxy.stateMode;
import com.rjxy.bean.EnemyPlane; 
/*
 * 第一关
 * @author LSR
 * 功能：设置敌机出现位置，使用小型飞机（ep01-05）
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


package com.rjxy.stateMode;
/*
 * 第三关
 * 设置敌机交替出现，前300时间片左侧出现(ep07),后300右侧出现
 * @author:LSR
 * */
import com.rjxy.bean.EnemyPlane;

public class Hero3State implements HeroState {

	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY, int count) {
		// TODO Auto-generated method stub
		if (count % 600<300) {//左1/2
	 		ep.setX((int)(int)(Math.random()*(bgX/2)));
	 		ep.changeImage(7,7);
		} else {//右1/2
			ep.setX((int)((int)(Math.random()*(bgX/2)) +(bgX/4)));
		}
		
		
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int)(Math.random()*2)+2);
		ep.changeImage(8,8);
	} 
	
}


package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;

//第一关：随机位置,从上方落
public class Hero1State implements HeroState{
	
	//第一关：设置位置，不使用时间片
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) {
		ep.setX((int) (Math.random() * bgX));
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 2) + 2);
		//第1关，图片01.png -03.png
		ep.changeImg(1,3);
	}
	
}

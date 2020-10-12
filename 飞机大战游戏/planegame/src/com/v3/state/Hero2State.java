package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;

//第2关：随机位置,从上方落
public class Hero2State implements HeroState{
	
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) 
	{
		// 上方敌机，出现在中间1/2区域，两侧1/4没有敌机
		// 设置位置，不用时间片
		int sx=bgX/4;
		ep.setX((int) (Math.random() * (bgX/2)+sx));
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 2) + 2);		
		ep.changeImg(4,6);
	}
}

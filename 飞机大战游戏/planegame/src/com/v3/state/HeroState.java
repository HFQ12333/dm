package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;

public interface HeroState {

	//设置某一关的数据
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count);
	
}

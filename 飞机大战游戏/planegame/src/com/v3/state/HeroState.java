package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;

public interface HeroState {

	//����ĳһ�ص�����
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count);
	
}

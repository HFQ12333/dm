package com.rjxy.stateMode;

import com.rjxy.bean.EnemyPlane;

public interface HeroState {
	
	//更新敌机参数 共有方法
	public void enemyUpdate(EnemyPlane ep, int bgX , int bgY , int count  ); 
		
	}

package com.rjxy.stateMode;

import com.rjxy.bean.EnemyPlane;

public interface HeroState {
	
	//���µл����� ���з���
	public void enemyUpdate(EnemyPlane ep, int bgX , int bgY , int count  ); 
		
	}

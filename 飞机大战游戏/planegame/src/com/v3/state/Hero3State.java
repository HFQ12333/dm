package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;
import com.rjxy.hujie.controler.MyJPanel;

//第3关：根据时间片，左右两个区域，交替出现敌机
//第3关：左侧绿色飞机 08.png，右侧红色飞机 07.png
public class Hero3State implements HeroState{
	
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) {
		int sx;
		//每次延时20ms, 每50个时间片算1秒
		//每间隔时间片600，前一段左侧出飞机，后一段右侧出飞机
		if(count % 600 < 300)
		{
			sx=(int) (Math.random() * bgX/2);
			ep.changeImg(8, 8);
		}
		else
		{
			sx=(int) (Math.random() * bgX/2)+ bgX/2;
			ep.changeImg(7, 7);
		}
			
		ep.setX(sx);
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 2) + 2);		
		
	}
}

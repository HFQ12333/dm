package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;
import com.rjxy.hujie.controler.MyJPanel;

//第4关：敌机速度加快
//第4关：根据时间片分三列，左，中，右，依次落下敌机
//每个区域，持续300时间片
public class Hero4State implements HeroState{
	
	@Override
	public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) {
 		
		int sx;
		if(count % 900 < 300)
		{
			sx=(int) (Math.random() * bgX/3);
		}
		else if(count % 900 < 600)
		{
			sx=(int) (Math.random() * bgX/3)+ bgX/3;
		}
		else  
		{
			sx=(int) (Math.random() * bgX/3)+ bgX*2/3;
		}
		if(Math.random() <0.5)
		{
			ep.changeImg(11, 11); //11.png
		}
		else
		{
			ep.changeImg(15, 15); //15.png 炸弹，碰到掉分
			ep.setName("e15");
		}
		ep.setX(sx);
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 3) + 3);	
	}
}

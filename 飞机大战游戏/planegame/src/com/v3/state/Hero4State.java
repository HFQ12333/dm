package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;
import com.rjxy.hujie.controler.MyJPanel;

//��4�أ��л��ٶȼӿ�
//��4�أ�����ʱ��Ƭ�����У����У��ң��������µл�
//ÿ�����򣬳���300ʱ��Ƭ
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
			ep.changeImg(15, 15); //15.png ը������������
			ep.setName("e15");
		}
		ep.setX(sx);
		ep.setR(25);
		ep.setY(-ep.getR());
		ep.setSpeed((int) (Math.random() * 3) + 3);	
	}
}

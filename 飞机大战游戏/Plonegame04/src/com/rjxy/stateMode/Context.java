package com.rjxy.stateMode;

import com.rjxy.bean.EnemyPlane;
import com.rjxy.bean.Hero;

public class Context {
	private HeroState s1,s2,s3,s4;//�ؿ�����
	private HeroState stateArray[];//�ؿ�����
	public int SceneLevel[]= new int[]{0,2000,4000,7000,10000};//200����ת�ڶ���,400������...
	private HeroState state;//��¼��ǰ�ؿ�
	public int stateNum=1;//��¼��ǰ�ؿ���
	
	public Context(){
		s1=new Hero1State();
		s2=new Hero2State();
		s3=new Hero3State();
		s4=new Hero4State();
		
		stateArray=new HeroState[]{s1,s2,s3,s4};
		state =s1;//���õ�һ��Ϊ��ǰ�ؿ�
		stateNum=1;
	}
	
	//�ؿ��л�
	public void change() {
		//����ģʽ��ȡս����ǰ����
		int score=Hero.getInstance().getScore();
		for(int i=0;i<SceneLevel.length-1;i++){
			if (score>=SceneLevel[i] && score<SceneLevel[i+1]) {
				state=stateArray[i];//���ݷ��������ùؿ�
				stateNum=i+1;
			}
		}
	}
	//���ò�ͬ״̬��ͨ����Ϊ
	public void enemyUpdate(EnemyPlane ep,int bgX,int bgY,int count) {
		state.enemyUpdate(ep, bgX, bgY, count);
	}
}
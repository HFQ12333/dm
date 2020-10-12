package com.rjxy.stateMode;

import com.rjxy.bean.EnemyPlane;
import com.rjxy.bean.Hero;

public class Context {
	private HeroState s1,s2,s3,s4;//关卡对象
	private HeroState stateArray[];//关卡数组
	public int SceneLevel[]= new int[]{0,2000,4000,7000,10000};//200分跳转第二关,400第三关...
	private HeroState state;//记录当前关卡
	public int stateNum=1;//记录当前关卡数
	
	public Context(){
		s1=new Hero1State();
		s2=new Hero2State();
		s3=new Hero3State();
		s4=new Hero4State();
		
		stateArray=new HeroState[]{s1,s2,s3,s4};
		state =s1;//设置第一关为当前关卡
		stateNum=1;
	}
	
	//关卡切换
	public void change() {
		//单例模式获取战机当前分数
		int score=Hero.getInstance().getScore();
		for(int i=0;i<SceneLevel.length-1;i++){
			if (score>=SceneLevel[i] && score<SceneLevel[i+1]) {
				state=stateArray[i];//根据分数档设置关卡
				stateNum=i+1;
			}
		}
	}
	//设置不同状态的通用行为
	public void enemyUpdate(EnemyPlane ep,int bgX,int bgY,int count) {
		state.enemyUpdate(ep, bgX, bgY, count);
	}
}
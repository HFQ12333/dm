package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;
import com.rjxy.hujie.bean.Hero;

public class Context {
		 public static int ScoreReduce=50;//碰到炸弹，扣分50
		 
		  private HeroState s1,s2,s3,s4; //各个关卡
		  private HeroState statearray[];
		  public int scenelevel[]=new int[]{0,200,400,700,10000};//达到200，进第2关
		   private HeroState state;
		   public int scenenum=1;// 第几关

		   public Context(){
			   s1=new Hero1State();
			   s2=new Hero2State();
			   s3=new Hero3State();
			   s4=new Hero4State();
			   statearray=new HeroState[]{s1,s2,s3,s4};
		      state = s1;	//默认第1关
		   }
		   // 改变状态
		   public void check(int count)
		   {
			  int s=  Hero.getInstance().getScore();
			  for( int i = 0; i < scenelevel.length-1; i++ )
			  {
				  if(s >= scenelevel[i] && s< scenelevel[i+1])
				  {
					  state =  statearray[i];
					  scenenum=i+1;// 1,2,3,4
					 // System.out.println("当前关："+scenenum);
				  }
			  }
			  if(s >= scenelevel[4]  )
				  state =  s4;
		   }
		   // 各个状态下的通用行为，设置不同关卡下的敌机数据
		   public void enemyUpdate(EnemyPlane ep, int bgX, int bgY,int count) {
			   state.enemyUpdate(ep, bgX, bgY,count);
		   }
		   public void setState(HeroState state){
		      this.state = state;     
		   }
		 
		   public HeroState getState(){
		      return state;
		   }
		}

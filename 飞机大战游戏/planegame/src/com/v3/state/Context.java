package com.v3.state;

import com.rjxy.hujie.bean.EnemyPlane;
import com.rjxy.hujie.bean.Hero;

public class Context {
		 public static int ScoreReduce=50;//����ը�����۷�50
		 
		  private HeroState s1,s2,s3,s4; //�����ؿ�
		  private HeroState statearray[];
		  public int scenelevel[]=new int[]{0,200,400,700,10000};//�ﵽ200������2��
		   private HeroState state;
		   public int scenenum=1;// �ڼ���

		   public Context(){
			   s1=new Hero1State();
			   s2=new Hero2State();
			   s3=new Hero3State();
			   s4=new Hero4State();
			   statearray=new HeroState[]{s1,s2,s3,s4};
		      state = s1;	//Ĭ�ϵ�1��
		   }
		   // �ı�״̬
		   public void check(int count)
		   {
			  int s=  Hero.getInstance().getScore();
			  for( int i = 0; i < scenelevel.length-1; i++ )
			  {
				  if(s >= scenelevel[i] && s< scenelevel[i+1])
				  {
					  state =  statearray[i];
					  scenenum=i+1;// 1,2,3,4
					 // System.out.println("��ǰ�أ�"+scenenum);
				  }
			  }
			  if(s >= scenelevel[4]  )
				  state =  s4;
		   }
		   // ����״̬�µ�ͨ����Ϊ�����ò�ͬ�ؿ��µĵл�����
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

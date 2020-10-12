package com.v2.memento;

import com.rjxy.hujie.controler.DB_Operater;

public class Caretaker {

	    /**
	     * 备忘录的取值方法
	     */
	  
	    public Memento retrieveMemento(String name) {
	    	// database
	    	DB_Operater db_Operater= new DB_Operater();
	    	 int level = db_Operater.get(name, "level");
	 		int grade = db_Operater.get(name, "grade");
	 		int time = db_Operater.get(name, "time");
	        return new Memento(name,grade,level,time);
	    }
	   
	    /**
	     * 备忘录的赋值方法
	     */
	    public void saveMemento(Memento memento) {
	    	// database
	    	DB_Operater db_Operater= new DB_Operater();
	    	
	        ////////////////
	    	// time 当前时间+累积时间
			int time = memento.getTime() + db_Operater.get(memento.getName(), "time");
			// 当前grade和原maxgrade求最大值
			int maxgrade = Math.max(memento.getGrade(), 
						db_Operater.get(memento.getName(), "maxgrade"));
			System.out.println("test:!!!"+maxgrade);
			//grade不累加，单次成绩
			db_Operater.update(memento.getName(), memento.getLevel(), memento.getGrade());
			db_Operater.update(memento.getName(), time+"", "time");
			db_Operater.update(memento.getName(), maxgrade+"", "maxgrade");
			//备忘录更新最大值
			memento.setMaxgrade(maxgrade);
			//
			System.out.println("tetttt:"+db_Operater.get(memento.getName(), "maxgrade"));
	    }
}

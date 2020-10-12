package com.v2.memento;

import com.rjxy.hujie.controler.DB_Operater;

public class Caretaker {

	    /**
	     * ����¼��ȡֵ����
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
	     * ����¼�ĸ�ֵ����
	     */
	    public void saveMemento(Memento memento) {
	    	// database
	    	DB_Operater db_Operater= new DB_Operater();
	    	
	        ////////////////
	    	// time ��ǰʱ��+�ۻ�ʱ��
			int time = memento.getTime() + db_Operater.get(memento.getName(), "time");
			// ��ǰgrade��ԭmaxgrade�����ֵ
			int maxgrade = Math.max(memento.getGrade(), 
						db_Operater.get(memento.getName(), "maxgrade"));
			System.out.println("test:!!!"+maxgrade);
			//grade���ۼӣ����γɼ�
			db_Operater.update(memento.getName(), memento.getLevel(), memento.getGrade());
			db_Operater.update(memento.getName(), time+"", "time");
			db_Operater.update(memento.getName(), maxgrade+"", "maxgrade");
			//����¼�������ֵ
			memento.setMaxgrade(maxgrade);
			//
			System.out.println("tetttt:"+db_Operater.get(memento.getName(), "maxgrade"));
	    }
}

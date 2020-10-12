package com.v2.memento;

public class Originator {
	 
	public Memento m;
	
    public Memento creatMementoObject() {
        m=new Memento();
        return m;
    }
    public Memento creatMementoObject(String name, int grade, int level, int time) {
        m=new Memento(  name,   grade,   level,   time);
        return m;
    }
    /**
     * 将发起人恢复到备忘录对象所记载的状态
     */
    public void restoreMemento(Memento memento) {
    	m=memento;
        System.out.println("恢复 备忘录 状态：" );
        System.out.println(m.getName()+","+m.getGrade()+","+m.getLevel());
    }
 
}

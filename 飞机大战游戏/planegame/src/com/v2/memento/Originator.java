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
     * �������˻ָ�������¼���������ص�״̬
     */
    public void restoreMemento(Memento memento) {
    	m=memento;
        System.out.println("�ָ� ����¼ ״̬��" );
        System.out.println(m.getName()+","+m.getGrade()+","+m.getLevel());
    }
 
}

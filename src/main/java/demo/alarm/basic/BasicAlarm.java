package demo.alarm.basic;

import demo.object.AlarmBean;

public abstract class BasicAlarm implements Runnable {

	public abstract void process() throws Exception;
}

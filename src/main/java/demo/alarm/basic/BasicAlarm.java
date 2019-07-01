package demo.alarm.basic;

import demo.object.AlarmBean;
import demo.exception.CustomException;

public abstract class BasicAlarm implements Runnable {

	public abstract void process() throws CustomException;
}

package demo.alarm;

import java.util.Map;
import java.util.Iterator;
import java.util.Map.Entry;
import java.lang.reflect.Constructor;
import org.apache.flink.types.Row;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.util.ParserAlarm;
import demo.alarm.basic.BasicAlarm;
import demo.object.AlarmBean;
import demo.common.Constants;
import demo.exception.CustomException;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class AlarmExecutor {
	
	private static final Logger _log = LoggerFactory.getLogger(AlarmExecutor.class);
	
	private Map<String, AlarmBean> alarms = ParserAlarm.getAlarmMap();

	public void run() throws CustomException {
		
//		List<Class<?>> claList = new ArrayList<>();
		Iterator<Entry<String, AlarmBean>> iterator = alarms.entrySet().iterator();
		try {
			while (iterator.hasNext()) {
			    Entry<String, AlarmBean> entry = iterator.next();
			    String key = entry.getKey();
			    AlarmBean alarm = entry.getValue();
			    String cls = alarm.getCls();
			    Class<?> c = Class.forName(cls);
			    Object obj = c.newInstance();
			    if (obj instanceof BasicAlarm) {
			    	Constructor con = c.getConstructor(AlarmBean.class);
			    	BasicAlarm ba = (BasicAlarm) con.newInstance(alarm);
					new Thread(ba).start();
				}
//			    claList.add(c);
			}
		} catch (ClassNotFoundException e) {
			_log.error(null, e);
			throw new CustomException("class not found");
		} catch (Exception e) {
			_log.error(null, e);
			e.printStackTrace();
			throw new CustomException(e.toString());
		}
	}
}
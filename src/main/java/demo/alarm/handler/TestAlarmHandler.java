package demo.alarm.handler;

import java.util.Map;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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

import demo.alarm.basic.BasicAlarm;
import demo.object.AlarmBean;
import demo.object.TableBean;
import demo.user.SourceStream;
import demo.util.ParserAlarm;
import demo.util.Restart;
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
public class TestAlarmHandler extends BasicAlarm {
	
	private static final Logger _log = LoggerFactory.getLogger(TestAlarmHandler.class);

	private AlarmBean alarm = null;
	
	public TestAlarmHandler() {
		alarm = null;
	}
	
	public TestAlarmHandler(AlarmBean alarm) {
		this.alarm = alarm;
	}
	
	@Override
	public void process() throws CustomException {
		try {
			System.out.println(this.alarm);
			String[] tableNames = this.alarm.getTables();
			if (null == tableNames || tableNames.length == 0)
				throw new CustomException("get the tables error");
			
			// set up the streaming execution environment
			StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
			env.enableCheckpointing(5000);
			env.setParallelism(1);
			Restart rs = new Restart();
			env = rs.setDelayRestart(env);
			SourceStream ss = new SourceStream();
			int i = 0;
			for (String tableName : tableNames) {
				DataStream<Row> stream = ss.getSourceStream(env, StringUtils.strip(tableName));
				System.out.println("+++++++++++++++++++:" + i + ":+++++++++++++");
				stream.print();
				i++;
			} 
			env.execute("Flink Streaming Java API Skeleton");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(e.toString());
		}
	}
	
	@Override
	public void run() {
		try {
		    process();
		} catch (CustomException e) {
			_log.error(null, e);
		}
	}
}
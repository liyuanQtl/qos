//package demo.alarm.handler;
//
//import java.util.Map;
//import java.util.List;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.flink.types.Row;
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
//import org.apache.flink.table.api.Table;
//import org.apache.flink.table.api.TableEnvironment;
//import org.apache.flink.table.api.java.StreamTableEnvironment;
//import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import demo.alarm.basic.BasicAlarm;
//import demo.object.AlarmBean;
//import demo.object.TableBean;
//import demo.source.KafkaSource;
//import demo.table.SqlOperator;
//import demo.table.StreamSetToTable;
//import demo.table.TableToStreamSet;
//import demo.util.ParserSql;
//import demo.exception.CustomException;
//
///**
// * Skeleton for a Flink Streaming Job.
// *
// * <p>For a tutorial how to write a Flink streaming application, check the
// * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
// *
// * <p>To package your application into a JAR file for execution, run
// * 'mvn clean package' on the command line.
// *
// * <p>If you change the name of the main class (with the public static void main(String[] args))
// * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
// */
//public class TestAlarm extends BasicAlarm {
//	
//	private static final Logger _log = LoggerFactory.getLogger(TestAlarm.class);
//
//	private AlarmBean alarm = null;
//	
//	public TestAlarm() {
//		alarm = null;
//	}
//	
//	public TestAlarm(AlarmBean alarm) {
//		this.alarm = alarm;
//	}
//	
//	@Override
//	public void process() throws CustomException {
//		System.out.println(this.alarm);
//		List<TableBean> tables = this.alarm.getTables();
//		if (null == tables)
//			throw new CustomException("get the tables error");
//		// set up the streaming execution environment
//		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//		env.enableCheckpointing(5000);
//		env.setParallelism(1);
//		KafkaSource source = new KafkaSource();
//		int i = 0;
//		for (TableBean table : tables) {
//			String topic = table.getTopic();
//			if (StringUtils.isEmpty(topic))
//				throw new CustomException("topic empty");
//			FlinkKafkaConsumer010<Row> myConsumer = source.getKafkaConsumer(topic);
//			DataStream<Row> stream = env.addSource(myConsumer);
//			i = i+1;
//			System.out.println(i+" source data"+stream.print());
//			StreamTableEnvironment sTableEnv = StreamTableEnvironment.getTableEnvironment(env);
//			StreamSetToTable toTable = new StreamSetToTable();
//			String tableName = table.getName();
//			if (StringUtils.isEmpty(tableName))
//				throw new CustomException("the table name is empty");
//			toTable.registerStream(sTableEnv, tableName, stream);
//			System.out.println("table out================================");
//			String sql = table.getSql();
//			if (StringUtils.isEmpty(sql))
//				throw new CustomException("the sql is empty");
//			String[] pieces = sql.toLowerCase().split(" ");
//			String p0 = StringUtils.strip(pieces[0]);
//			SqlOperator oper = new SqlOperator();
//			try {
//				if ("select".equals(p0)) {
//					Table sqlResult = oper.queryTable(sTableEnv, sql);
//					DataStream<Row> resultSet = sTableEnv.toAppendStream(sqlResult, Row.class);
//					System.out.println(i + " result out:"+resultSet.print());
//				}
//				else if ("update".equals(p0))
//					oper.updateTable(sTableEnv, sql);
//			} catch (CustomException e) {
//				_log.error("sql operator error", e);
//			}
//		} 
//		env.execute("Flink Streaming Java API Skeleton");
//	}
//	
//	@Override
//	public void run() {
//		try {
//		    process();
//		} catch (CustomException e) {
//			_log.error(null, e);
//		}
//	}
//}
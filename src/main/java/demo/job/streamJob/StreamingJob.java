package demo.job.streamJob;

import java.util.Map;
import org.apache.flink.types.Row;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
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

import demo.source.KafkaSource;
import demo.table.StreamSetToTable;
import demo.util.ParserSql;
import demo.object.AlarmBean;
import demo.alarm.AlarmExecutor;

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
public class StreamingJob {
	
	private static final Logger _log = LoggerFactory.getLogger(StreamingJob.class);

	public static void main(String[] args) throws Exception {
		AlarmExecutor exe = new AlarmExecutor();
		exe.run();
//		// set up the streaming execution environment
//		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//		env.enableCheckpointing(5000);
//		env.setParallelism(1);
//		KafkaSource source = new KafkaSource();
//		FlinkKafkaConsumer010<Row> myConsumer = source.getKafkaConsumer();
//		DataStream<Row> stream = env.addSource(myConsumer);
////		stream.print();
////		FlinkKafkaConsumer010<String> myConsumer = source.getKafkaConsumer();
////		DataStream<String> stream = env.addSource(myConsumer);
//		System.out.println("first stream print");
//		stream.print();
//		StreamTableEnvironment sTableEnv = StreamTableEnvironment.getTableEnvironment(env);
//		StreamSetToTable toTable = new StreamSetToTable();
////		Map<String, AlarmBean> sqlMap = ParserSql.getSqlMap();
//		
//		String tableName = "testKafkaTable";
//		toTable.registerStream(sTableEnv, tableName, stream);
//		System.out.println("table out================================");
//		String sql = "select rt, time_stamp, hdt_access_domain from testKafkaTable";
//		Table sqlResult  = sTableEnv.sqlQuery(sql);
//		sqlResult.printSchema();
////		String[] names = {"rt", "time_stamp", "hdt_access_domain"};
////		TypeInformation<?>[] types = {BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.LONG_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO};
////		TypeInformation<Row> rowType = new RowTypeInfo(types, names);
//		DataStream<Row> resultSet = sTableEnv.toAppendStream(sqlResult, Row.class);
//		resultSet.print();
//		env.execute("Flink Streaming Java API Skeleton");
	}
}
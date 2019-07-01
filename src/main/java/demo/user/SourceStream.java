package demo.user;

import java.util.Map;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.types.Row;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.object.TableBean;
import demo.source.KafkaSource;
import demo.table.SqlOperator;
import demo.table.StreamSetToTable;
import demo.table.TableToStreamSet;
import demo.util.ParserTable;
import demo.user.TimeAttr;
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
public class SourceStream {
	
	private static final Logger _log = LoggerFactory.getLogger(SourceStream.class);

	private static Map<String, TableBean> tableMap = ParserTable.getTableMap();
	
	public DataStream<Row> getSourceStream(StreamExecutionEnvironment env, String tableName) throws CustomException {
		try {
			if (StringUtils.isEmpty(tableName))
				throw new CustomException("the table name is empty");
			tableName = StringUtils.strip(tableName);
			KafkaSource source = new KafkaSource();
			System.out.println("table name:"+tableName);
			TableBean table = tableMap.get(tableName);
			if (null == table) {
				System.out.println("table name:"+tableName);
				throw new CustomException("the table name does not exist");
			}
			String topic = table.getTopic();
			if (StringUtils.isEmpty(topic))
				throw new CustomException("topic empty");
			String timeAttr = table.getTimeAttr();
			String attr = "";
			if (StringUtils.isNotEmpty(timeAttr)) {
				attr = StringUtils.strip(timeAttr.toLowerCase());
				setEnvTimeChara(env, attr);
			}
			FlinkKafkaConsumer010<Row> myConsumer = source.getKafkaConsumer(topic);
			DataStream<Row> stream = env.addSource(myConsumer);
			stream.print();
			assignWaterMark(stream, attr);
			StreamTableEnvironment sTableEnv = StreamTableEnvironment.getTableEnvironment(env);
			StreamSetToTable toTable = new StreamSetToTable();
			toTable.registerStream(sTableEnv, tableName, stream);
			System.out.println("table out================================");
			String sql = reorganizeSQL(table);
			if (StringUtils.isEmpty(sql))
				throw new CustomException("the sql is empty");
			String[] pieces = sql.toLowerCase().split(" ");
			String p0 = StringUtils.strip(pieces[0]);
			SqlOperator oper = new SqlOperator();
		
			if ("select".equals(p0)) {
				Table sqlResult = oper.queryTable(sTableEnv, sql);
				sqlResult.printSchema();
				DataStream<Row> resultSet = sTableEnv.toAppendStream(sqlResult, Row.class);
				return resultSet;
			} else if ("update".equals(p0)) {
				oper.updateTable(sTableEnv, sql);
				return null;
			} else
				throw new CustomException("sql error");
		} catch (Exception e) {
			_log.error("sql operator error", e);
			throw new CustomException(e.toString());
		}
	}
	
	public void setEnvTimeChara(StreamExecutionEnvironment env, String attr) throws CustomException {
		if (StringUtils.isNotEmpty(attr)) {
			String attrLower = StringUtils.strip(attr.toLowerCase());
			if (attrLower.equals("eventtime")) {
				env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
			} else if (attrLower.equals("processingtime")) {
				env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
			} else if (attrLower.equals("ingestiontime")) {
				env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
			} else
				throw new CustomException("time attr value error");
		}
	}
	
	public void assignWaterMark(DataStream<Row> stream, String timeAttr) throws CustomException {
		if (StringUtils.isNotEmpty(timeAttr)) {
			String attrLower = StringUtils.strip(timeAttr.toLowerCase());
			if ("eventtime".equals(attrLower)) {
				try {
					TypeInformation<Row> typeInfo = stream.getType();
			    	stream.assignTimestampsAndWatermarks(new CustomPeriodicWatermark((RowTypeInfo)typeInfo, Constants.TIMESTAMP_NAME));
				} catch (Exception e) {
					_log.error(null, e);
					throw new CustomException(e.toString());
				}
			}
		}
	}
	
	public String  reorganizeSQL(TableBean table) {
		String sql = table.getSql();
//		params to be organized
		return sql;
	}
}
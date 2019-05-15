////package demo;
////
////import org.apache.flink.api.common.serialization.SimpleStringSchema;
////import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
////import org.apache.flink.streaming.api.datastream.DataStream;
////import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import java.util.Properties;
////import demo.KafkaConnect;
////
/////**
//// * Skeleton for a Flink Streaming Job.
//// *
//// * <p>For a tutorial how to write a Flink streaming application, check the
//// * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
//// *
//// * <p>To package your application into a JAR file for execution, run
//// * 'mvn clean package' on the command line.
//// *
//// * <p>If you change the name of the main class (with the public static void main(String[] args))
//// * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
//// */
////public class StreamingJob {
////	
////	private static final Logger _log = LoggerFactory.getLogger(StreamingJob.class);
////
////	public static void main(String[] args) throws Exception {
////		// set up the streaming execution environment
////		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
////		env.enableCheckpointing(5000);
////
////		/*
////		 * Here, you can start creating your execution plan for Flink.
////		 *
////		 * Start with getting some data from the environment, like
////		 * 	env.readTextFile(textPath);
////		 *
////		 * then, transform the resulting DataStream<String> using operations
////		 * like
////		 * 	.filter()
////		 * 	.flatMap()
////		 * 	.join()
////		 * 	.coGroup()
////		 *
////		 * and many more.
////		 * Have a look at the programming guide for the Java API:
////		 *
////		 * http://flink.apache.org/docs/latest/apis/streaming/index.html
////		 *
////		 */
////
////		// execute program
////		
//////		Properties properties = new Properties();
//////		properties.setProperty("bootstrap.servers", "np04:9092,np20:9092,np25:9092,np26:9092,np29:9092");
//////		// only required for Kafka 0.8
//////		properties.setProperty("zookeeper.connect", "np04:50000/kafka101,np05:50000/kafka101,np06:50000/kafka101");
//////		properties.setProperty("group.id", "kafka101_test");
//////		FlinkKafkaConsumer010<String> myConsumer =
//////			    new FlinkKafkaConsumer010<>("hdtaccesskafka", new SimpleStringSchema(), properties);
////		FlinkKafkaConsumer010<String> myConsumer = KafkaConnect.getKafkaConsumer("/kafka.properties", null);
//////			myConsumer.assignTimestampsAndWatermarks(new CustomWatermarkEmitter());
////
////		DataStream<String> stream = env.addSource(myConsumer);
////		stream.print();
//////		FlinkKafkaConsumer08<String> myConsumer = new FlinkKafkaConsumer08<>(...);
//////		myConsumer.setStartFromEarliest();     // start from the earliest record possible
//////		myConsumer.setStartFromLatest();       // start from the latest record
//////		myConsumer.setStartFromTimestamp(...); // start from specified epoch timestamp (milliseconds)
//////		myConsumer.setStartFromGroupOffsets(); // the default behaviour
//////
//////		DataStream<String> stream = env.addSource(myConsumer);
////		stream.writeAsText("results");
////		env.execute("Flink Streaming Java API Skeleton");
////	}
////}
//
//package demo;
//
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.api.common.functions.ReduceFunction;
//import org.apache.flink.api.common.serialization.SimpleStringSchema;
//import org.apache.flink.streaming.api.TimeCharacteristic;
//import org.apache.flink.streaming.api.windowing.time.Time;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
//import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
//import org.apache.flink.api.common.serialization.DeserializationSchema;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import java.util.Properties;
//
//import demo.KafkaConnect;
//import demo.WriteTextSink;
//import demo.FlowSchema;
////import demo.AggreFun;
//import demo.CustomPunctuatedWatermark;
//import object.Flow;
//
//public class StreamingJob {
//	
//	private static final Logger _log = LoggerFactory.getLogger(StreamingJob.class);
//
//	public static void main(String[] args) throws Exception {
//		// set up the streaming execution environment
//		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//		StreamTableEnvironment tableEnv = TableEnvironment.getTableEnvironment(env);
////		env.enableCheckpointing(5000);
//		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//		DeserializationSchema<Flow> schema = new FlowSchema();
//		CustomPunctuatedWatermark wm = new CustomPunctuatedWatermark();
//		DataStream<Flow> stream = env.addSource(KafkaConnect.getKafkaConsumer(schema, wm)).setParallelism(1);
//		DataStream<Flow> stream_r = stream
//		      .keyBy("hdt_tp_domain")
//		      .window(SlidingEventTimeWindows.of(Time.minutes(1), Time.minutes(1)))
////		      .timeWindow(Time.minutes(5), Time.minutes(1))
////		      .aggregate(new AggreFun())
////		      .setParallelism(1)
//		      .reduce(new ReduceFunction<Flow>() {
//		          @Override
//		    	  public Flow reduce(Flow f1, Flow f2) throws Exception {
//					  Flow r = new Flow();
//					  r.setDomain(f2.getDomain());
//					  r.setSend(f1.getSend() + f2.getSend());
//					  r.setRecv(f1.getRecv() + f2.getRecv());
//					  r.setMsec(f1.getMsec() + f2.getMsec());
//					  r.setDate(f2.getDate());
////					  System.out.println("result domain:"+r.toString());
//					  return r;
//		    	  }
//		      })
//			  .map(new MapFunction<Flow, Flow>() {
//		      @Override
//		      public Flow map(Flow flow) throws Exception {
//		          System.out.println("Flow String:"+flow.toString());
//		          return flow;
//		      }
//		  });
////		stream_r.writeAsText("results");
//		stream_r.addSink(new WriteTextSink());
////			  .map(new MapFunction<Flow, Flow>() {
////			      @Override
////			      public Flow map(Flow flow) throws Exception {
////			          System.out.println("flow.domain:"+flow.getDomain() + "flow.send:"+flow.getSend());
////			          return flow;
////			      }
////			  });
//		env.execute("Flink Streaming Java API Skeleton");
//	}
//}
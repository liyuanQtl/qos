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
//public class TransformationStream {
//	
//	private static final Logger _log = LoggerFactory.getLogger(TransformationStream.class);
//
//	public <T> DataStream<T> streamTransformation(DataStream<T> stream) throws Exception {
//		DataStream<T> stream_r = stream
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
//package demo.sink;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import org.apache.flink.streaming.api.functions.sink.SinkFunction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import demo.object.Flow;
//
//public class FileSink implements SinkFunction<Flow> {
//	
//	private static final Logger _log = LoggerFactory.getLogger(FileSink.class);
//    
//	@Override 
//    public void invoke(Flow flow) {
//        try{
//        	File dir = new File("E:\\myworkplace\\eclipse\\qos-demo\\results");
//        	if (!dir.exists()) {
//        		dir.mkdir();
//        	}
//        	
//        	Long ts = flow.getDate().getTime();
//        	String filename = "E:\\myworkplace\\eclipse\\qos-demo\\results\\" + ts + ".txt";
//            File file =new File(filename);
//            //if file doesnt exists, then create it
//            if(!file.exists()){
//                file.createNewFile();
//            }
//
//            System.out.println("filename:" + file.getName());
//            //true = append file
//            FileWriter fileWritter = new FileWriter(file, true);
//            System.out.println("flow.toString():" + flow.toString());
//            fileWritter.write(flow.toString()+"\n");
//            fileWritter.close();
//
//       } catch(IOException e){
//           e.printStackTrace();
//       }
//    }
//}
//
//import org.apache.flink.api.common.serialization.SimpleStringEncoder;
//import org.apache.flink.core.fs.Path;
//import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
//
//DataStream<String> input = ...;
//
//final StreamingFileSink<String> sink = StreamingFileSink
//	.forRowFormat(new Path(outputPath), new SimpleStringEncoder<>("UTF-8"))
//	.build();
//
//input.addSink(sink);
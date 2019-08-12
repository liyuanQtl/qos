package demo.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.PrintStream;

import org.apache.flink.api.common.serialization.Encoder;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;

public class FileSink {
	
	private static final Logger _log = LoggerFactory.getLogger(FileSink.class);
    
	public StreamingFileSink<Row> getFileSink(String outputPath, DataStream<Row> stream) {
		StreamingFileSink<Row> sink = StreamingFileSink
			.forRowFormat(new Path(outputPath), (Encoder<Row>) (element, stream) -> {
				PrintStream out = new PrintStream(stream);
				out.println(element.f1);
			})
			.withBucketAssigner(new KeyBucketAssigner())
			.build();
	    return sink;
	}
	
	public class KeyBucketAssigner implements BucketAssigner<Row, String> {

		private static final long serialVersionUID = 987325769970523326L;

		@Override
		public String getBucketId(Row element, Context context) {
			String f0 = String.valueOf(element.getField(0)); 
			return f0;
		}

		@Override
		public SimpleVersionedSerializer<String> getSerializer() {
			return SimpleVersionedStringSerializer.INSTANCE;
		}
	}
}
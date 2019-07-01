package demo.user;

import org.apache.flink.types.Row;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomPeriodicWatermark implements AssignerWithPeriodicWatermarks<Row> {
	
	private static final Logger _log = LoggerFactory.getLogger(CustomPeriodicWatermark.class);

    private RowTypeInfo typeInfo = null;
    private String timeFieldName = null;
    private final long maxOutOfOrderness = 3500; // 3.5 seconds
    private long currentMaxTimestamp;
    
    public CustomPeriodicWatermark(RowTypeInfo typeInfo, String timeFieldName) {
    	this.typeInfo = typeInfo;
    	this.timeFieldName = timeFieldName;
    }
    
    public CustomPeriodicWatermark() {
    	super();
    }
    
    @Override
    public long extractTimestamp(Row element, long previousElementTimestamp) {
        // TODO Auto-generated method stub
    	int index = typeInfo.getFieldIndex(timeFieldName);
        long timestamp = (Long) element.getField(index); 
        currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp);
        return timestamp;
    }

    @Override
    public Watermark getCurrentWatermark() {
        // TODO Auto-generated method stub
        return new Watermark(currentMaxTimestamp - maxOutOfOrderness);
    }
}

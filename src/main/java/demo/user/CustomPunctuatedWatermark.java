package demo.user;

import org.apache.flink.types.Row;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomPunctuatedWatermark implements AssignerWithPunctuatedWatermarks<Row> {
	
	private static final Logger _log = LoggerFactory.getLogger(CustomPunctuatedWatermark.class);

    private RowTypeInfo typeInfo = null;
    private String timeFieldName = null;
	
    public CustomPunctuatedWatermark(RowTypeInfo typeInfo, String timeFieldName) {
    	this.typeInfo = typeInfo;
    	this.timeFieldName = timeFieldName;
    }
    
    public CustomPunctuatedWatermark() {
    	super();
    }
    
    @Override
    public long extractTimestamp(Row element, long previousElementTimestamp) {
    	int index = typeInfo.getFieldIndex(timeFieldName);
        long timestamp = (Long) element.getField(index); 
        return timestamp;
    }

    @Override
    public Watermark checkAndGetNextWatermark(Row lastElement, long extractedTimestamp) {
//    	if (lastElement.hasWatermarkMarker())
    	    return new Watermark(extractedTimestamp);
//        return null ;
    }
}

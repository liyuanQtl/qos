package demo.common;

public class Constants {
    public enum SOURCE_TYPE {
        KAFKA_JSON, KAFKA_AVRO, CSV, TXT;
    }
    public enum SINK_TYPE {
        OCEAN_API, TXT;
    }
    
    public enum WINDOW_ASSIGNER {
        TUMBLING, SLIDING, SESSION, GLOBAL;
    }
    
    public enum KAFKA_CONSUMER_START {
        LATEST("latest"), EARLIEST("earliest"), TOPICPOSITION("topicPosition"), TIMESTAMP("timestamp"), GROUPOFFSETS("groupOffsets");
        private String offset;
        KAFKA_CONSUMER_START(String offset)
        {
            this.offset = offset;
        }
        public String getOffset() {
            return this.offset;
        }
        public static KAFKA_CONSUMER_START fromOffset(String os) {
        	String offset = os.toLowerCase();
        	if (offset.equals(""))
        		return GROUPOFFSETS;
        	for (KAFKA_CONSUMER_START type : KAFKA_CONSUMER_START.values()) { 
        		if (type.getOffset().equals(offset)) { 
        			return type; 
        		} 
        	} 
        	return null; 
        } 
    }
    
    public static final String FILE_PROP_NAME   = "file.yaml";
    public static final String KAFKA_PROP_NAME = "kafka.yaml";
    public static final String GROUP_WINDOW = "groupwindow";
    public static final String OVER_WINDOW = "overwindow";
    public static final String TIMESTAMP_NAME = "time_stamp";
}

package demo.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.types.Row;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.user.CustomPeriodicWatermark;
import demo.exception.CustomException;

public class TimeAttr {
	
	private static final Logger _log = LoggerFactory.getLogger(TimeAttr.class);
	
	private TimeCharacteristic timeAttr = null;
	private String timeFieldName = null;
	
	public void setTimeAttr(String attr, String timeFieldName) throws CustomException {
		if (StringUtils.isEmpty(attr) || StringUtils.isEmpty(timeFieldName))
			throw new CustomException("attr empty or timeFieldName empty");
		String attrLower = StringUtils.strip(attr.toLowerCase());
		if (attrLower.equals("eventtime")) {
			timeAttr = TimeCharacteristic.EventTime;
			this.timeFieldName = timeFieldName;
		} else if (attrLower.equals("processtime")) {
			timeAttr = TimeCharacteristic.ProcessingTime;
		} else if (attrLower.equals("ingestiontime")) {
			timeAttr = TimeCharacteristic.IngestionTime;
			this.timeFieldName = timeFieldName;
		} else
			throw new CustomException("time attr value error");
	}

	public StreamExecutionEnvironment setEnvTimeChara(StreamExecutionEnvironment env) {
		if (null != timeAttr) {
			env.setStreamTimeCharacteristic(timeAttr);
		}
		return env;
	}
	
	public void assignWaterMark(DataStream<Row> stream) throws CustomException {
		if (timeAttr == TimeCharacteristic.EventTime) {
			try {
				TypeInformation<Row> typeInfo = stream.getType();
		    	stream.assignTimestampsAndWatermarks(new CustomPeriodicWatermark((RowTypeInfo)typeInfo, timeFieldName));
			} catch (Exception e) {
				_log.error(null, e);
				throw new CustomException(e.toString());
			}
		}
	}

	public TimeCharacteristic getTimeAttr() {
		return timeAttr;
	}
	
	public String getTimeField() {
		return timeFieldName;
	}
}
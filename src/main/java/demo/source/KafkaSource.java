/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.source;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.types.Row;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaTopicPartition;
import org.apache.flink.streaming.util.serialization.JSONDeserializationSchema;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;

import demo.util.PropertyReader;
import demo.util.ParserTopic;
import demo.object.Column;
import demo.object.Topic;
import demo.user.CustomSchema;
import demo.common.Constants.KAFKA_CONSUMER_START;

public class KafkaSource {
	
	private static final Logger _log = LoggerFactory.getLogger(KafkaSource.class);
	
	private static final Properties properties = getKafkaConf();
	private static final Map<String, Topic> topicsInfo = getTopicInfo();
	
	private final static Properties getKafkaConf() {
		Properties properties = PropertyReader.getProp("/kafkaConf.properties");
		System.out.println("topic:"+properties.getProperty("topic"));
		_log.info("topic:"+properties.getProperty("topic"));
		return properties;
	}
	
	private final static Map<String, Topic> getTopicInfo() {
		return ParserTopic.getTopics("/kafkaTopicsInfo.yaml");
	}
	
	public void setStartFrom(FlinkKafkaConsumer010<Row> consumer) throws Exception {
		String start = properties.getProperty("start.position");
		if (null == start)
			start = "";
		KAFKA_CONSUMER_START kcs = KAFKA_CONSUMER_START.fromOffset(start);
		System.out.println("kafka consumer start:"+kcs);
		try {
			switch(kcs){
			    case EARLIEST :
			        consumer.setStartFromEarliest();
			        break;
			    case LATEST :
			    	consumer.setStartFromLatest();
			        break;
			    case TOPICPOSITION :
			    	String[] topics = properties.getProperty("start.position").split(";");
			    	String[] topicPositions = properties.getProperty("topicPosition").split(";");
			    	String[] topicOffsets = properties.getProperty("topicOffset").split(";");
			    	if (topics.length != topicPositions.length || topics.length != topicOffsets.length)
			    		throw new Exception("topics do not equal positon or offset");
			    	Map<KafkaTopicPartition, Long> specificStartOffsets = new LinkedHashMap<>();
			    	for (int i = 0; i < topics.length; i++) 
			    	    specificStartOffsets.put(new KafkaTopicPartition(topics[i], Integer.valueOf(topicPositions[i])), Long.valueOf(topicOffsets[i]));
			    	consumer.setStartFromSpecificOffsets(specificStartOffsets);
			    	break;
			    //你可以有任意数量的case语句
			    case TIMESTAMP :
			    	Long timestamp = Long.valueOf(properties.getProperty("timestamp"));
			    	consumer.setStartFromTimestamp(timestamp);
			    	break;
			    case GROUPOFFSETS :
			    	consumer.setStartFromGroupOffsets();
			}
		} catch (Exception e) {
			_log.error(null, e);
		}

	}
	
	public FlinkKafkaConsumer010<Row> getKafkaConsumer() throws Exception {
		System.out.println("kafka source get consumer");
//		String[] topicsInfo = properties.getProperty("topic").split(";");
//		for (String topic : topics) {
//			
//		} 
		String topic = properties.getProperty("topic");
		System.out.println("topicsInfo.get(topic):"+topicsInfo.get(topic));
		try {
			FlinkKafkaConsumer010<Row> myConsumer =
				    new FlinkKafkaConsumer010<>(topic, new CustomSchema(topicsInfo.get(topic)), properties);
//			FlinkKafkaConsumer010<String> myConsumer =
//		            new FlinkKafkaConsumer010<>(properties.getProperty("topic"), new SimpleStringSchema(), properties);
			setStartFrom(myConsumer);
			return myConsumer;
		} catch (Exception e) {
			System.out.println("error" + e);
			return null;
		}
	}

//	public FlinkKafkaConsumer010<Row> multiTopicConsumer() throws Exception {}
	
	public FlinkKafkaConsumer010<Row> getKafkaConsumer(String topic) throws Exception {
		System.out.println("kafka source get consumer");
		try {
			FlinkKafkaConsumer010<Row> myConsumer =
				    new FlinkKafkaConsumer010<>(topic, new CustomSchema(topicsInfo.get(topic)), properties);
			setStartFrom(myConsumer);
			return myConsumer;
		} catch (Exception e) {
			System.out.println("error" + e);
			return null;
		}
	}
	
//	public final static <T> KafkaAvroTableSource<T> getKafkaAvroTableSource(DeserializationSchema<T> schema, AssignerWithPunctuatedWatermarks<T> wm) throws Exception {
//		Class<? extends SpecificRecord> clazz = MyAvroType.class; 
//		KafkaAvroTableSource kafkaTableSource = new Kafka010AvroTableSource(
//		    kafkaTopic,
//		    kafkaProperties,
//		    clazz);
//		return kafkaTableSource;
//	}
//	
//	public final static <T> KafkaJsonTableSource<T> getKafkaJsonTableSource(DeserializationSchema<T> schema, AssignerWithPunctuatedWatermarks<T> wm) throws Exception {
//		Properties properties = getKafkaProp();
//		String[] fields = getFields();
//		// specify JSON field names and types
//		TypeInformation<Row> typeInfo = Types.ROW(
//				fields,
//		  new TypeInformation<?>[] { Types.INT(), Types.STRING(), Types.DOUBLE() }
//		);
//
//		KafkaJsonTableSource kafkaTableSource = new Kafka010JsonTableSource(
//			properties.getProperty("topic"),
//			properties,
//		    typeInfo);
//
//		// Fail on missing JSON field. By default, a missing JSON field does not fail the source. You can configure this via.
//		tableSource.setFailOnMissingField(true);
//		return kafkaTableSource;
//	}
}

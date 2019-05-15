///*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package demo.source;
//
//import java.util.Properties;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.flink.api.common.serialization.SimpleStringSchema;
//import org.apache.flink.api.common.serialization.DeserializationSchema;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
//import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
//
//public class KafkaSource {
//	
//	private static final Logger _log = LoggerFactory.getLogger(KafkaSource.class);
//	
//	private final static Properties getKafkaProp() {
//		Properties properties = PropertyReader.getProp("/kafka.properties");
//		System.out.println("topic:"+properties.getProperty("topic"));
//		_log.info("topic:"+properties.getProperty("topic"));
//		return properties;
//	}
//	
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
//}

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

package demo.table;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;

public class StreamSetToTable {
	
	private static final Logger _log = LoggerFactory.getLogger(StreamSetToTable.class);
	
	public Table streamToTable(DataStream stream) throws Exception {
		// Convert the DataStream into a Table with default fields "f0", "f1"
		Table table1 = tableEnv.fromDataStream(stream);

		// Convert the DataStream into a Table with fields "myLong", "myString"
		Table table2 = tableEnv.fromDataStream(stream, "myLong, myString");
		return table1;
	}
	
	public Table setToTable(DataSet set) throws Exception {
		// Convert the DataStream into a Table with default fields "f0", "f1"
		Table table1 = tableEnv.fromDataSet(set);

		// Convert the DataStream into a Table with fields "myLong", "myString"
		Table table2 = tableEnv.fromDataStream(set, "myLong, myString");
		return table1;
	}
}

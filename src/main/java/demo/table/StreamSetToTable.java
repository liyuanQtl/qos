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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import java.util.Map.Entry;

import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;

import demo.exception.CustomException;

public class StreamSetToTable {
	
	private static final Logger _log = LoggerFactory.getLogger(StreamSetToTable.class);
	
	public Table streamToTable(StreamTableEnvironment tableEnv, DataStream stream) throws CustomException {
		// Convert the DataStream into a Table with default fields "f0", "f1"
		Table table1 = tableEnv.fromDataStream(stream);

		// Convert the DataStream into a Table with fields "myLong", "myString"
//		Table table2 = tableEnv.fromDataStream(stream, "myLong, myString");
		return table1;
	}
	
	public Table setToTable(BatchTableEnvironment tableEnv, DataSet set) throws CustomException {
		// Convert the DataSet into a Table with default fields "f0", "f1"
		Table table1 = tableEnv.fromDataSet(set);

		// Convert the DataSet into a Table with fields "myLong", "myString"
		Table table2 = tableEnv.fromDataSet(set, "myLong, myString");
		return table1;
	}
	
	public void registerStream(StreamTableEnvironment tableEnv, String tableName, DataStream<Row> stream) throws CustomException {
		tableEnv.registerDataStream(tableName, stream);
	}
}

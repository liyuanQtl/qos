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

public class TableSchema {
	
	private static final Logger _log = LoggerFactory.getLogger(TableSchema.class);
	
	public Table queryTable(StreamTableEnvironment tableEnv, String sql) throws Exception {
		// compute revenue for all customers from France
		Table revenue = tableEnv.sqlQuery(sql);
		return revenue;
	}

	public Table queryTable(BatchTableEnvironment tableEnv, String sql) throws Exception {
		// compute revenue for all customers from France
		Table revenue = tableEnv.sqlQuery(sql);
		return Table;
	}
	public StreamTableEnvironment updateTable(StreamTableEnvironment tableEnv, String updateSql) throws Exception {
		// compute revenue for all customers from France and emit to "RevenueFrance"
		tableEnv.sqlUpdate(sql);
		return tableEnv;
	}

	public StreamTableEnvironment updateTable(BatchTableEnvironment tableEnv, String updateSql) throws Exception {
		// compute revenue for all customers from France and emit to "RevenueFrance"
		tableEnv.sqlUpdate(sql);
		return tableEnv;
	}
}

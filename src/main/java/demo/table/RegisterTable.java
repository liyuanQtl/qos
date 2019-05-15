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

public class RegisterTable {
	
	private static final Logger _log = LoggerFactory.getLogger(RegisterTable.class);
	
	public StreamTableEnvironment registerTable(StreamTableEnvironment tableEnv) throws Exception {
		// Table is the result of a simple projection query 
		Table projTable = tableEnv.scan("X").select(...);

		// register the Table projTable as table "projectedX"
		tableEnv.registerTable("projectedTable", projTable);
		return tableEnv;
	}

	public BatchTableEnvironment registerTable(BatchTableEnvironment tableEnv) throws Exception {
		// Table is the result of a simple projection query 
		Table projTable = tableEnv.scan("X").select(...);

		// register the Table projTable as table "projectedX"
		tableEnv.registerTable("projectedTable", projTable);
		return tableEnv;
	}
	public StreamTableEnvironment registerTableSource(StreamTableEnvironment tableEnv) throws Exception {
		// create a TableSource
		TableSource csvSource = new CsvTableSource("/path/to/file", ...);

		// register the TableSource as table "CsvTable"
		tableEnv.registerTableSource("CsvTable", csvSource);
		return tableEnv;
	}
	public BatchTableEnvironment registerTableSource(BatchTableEnvironment tableEnv) throws Exception {
		// create a TableSource
		TableSource csvSource = new CsvTableSource("/path/to/file", ...);

		// register the TableSource as table "CsvTable"
		tableEnv.registerTableSource("CsvTable", csvSource);
		return tableEnv;
	}
	public StreamTableEnvironment registerTableSink(StreamTableEnvironment tableEnv) throws Exception {
		// create a TableSink
		TableSink csvSink = new CsvTableSink("/path/to/file", ...);

		// define the field names and types
		String[] fieldNames = {"a", "b", "c"};
		TypeInformation[] fieldTypes = {Types.INT, Types.STRING, Types.LONG};

		// register the TableSink as table "CsvSinkTable"
		tableEnv.registerTableSink("CsvSinkTable", fieldNames, fieldTypes, csvSink);
		return tableEnv;
	}
	public BatchTableEnvironment registerTableSink(BatchTableEnvironment tableEnv) throws Exception {
		// create a TableSink
		TableSink csvSink = new CsvTableSink("/path/to/file", ...);

		// define the field names and types
		String[] fieldNames = {"a", "b", "c"};
		TypeInformation[] fieldTypes = {Types.INT, Types.STRING, Types.LONG};

		// register the TableSink as table "CsvSinkTable"
		tableEnv.registerTableSink("CsvSinkTable", fieldNames, fieldTypes, csvSink);
		return tableEnv;
	}
	public StreamTableEnvironment registerCatalog(StreamTableEnvironment tableEnv) throws Exception {
		// create an external catalog
		ExternalCatalog catalog = new InMemoryExternalCatalog();

		// register the ExternalCatalog catalog
		tableEnv.registerExternalCatalog("InMemCatalog", catalog);
		return tableEnv;
	}
	public BatchTableEnvironment registerCatalog(BatchTableEnvironment tableEnv) throws Exception {
		// create an external catalog
		ExternalCatalog catalog = new InMemoryExternalCatalog();

		// register the ExternalCatalog catalog
		tableEnv.registerExternalCatalog("InMemCatalog", catalog);
		return tableEnv;
	}
	public StreamTableEnvironment registerCatalog(DataStream stream) throws Exception {
		// register the DataStream as Table "myTable" with fields "f0", "f1"
		tableEnv.registerDataStream("myTable", stream);

		// register the DataStream as table "myTable2" with fields "myLong", "myString"
		tableEnv.registerDataStream("myTable2", stream, "myLong, myString");
		return tableEnv;
	}
	public BatchTableEnvironment registerCatalog(DataSet set) throws Exception {
		// register the DataSet as Table "myTable" with fields "f0", "f1"
		tableEnv.registerDataSet("myTable", set);

		// register the DataSet as table "myTable2" with fields "myLong", "myString"
		tableEnv.registerDataSet("myTable2", set, "myLong, myString");
		return tableEnv;
	}
}

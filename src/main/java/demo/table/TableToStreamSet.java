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
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.types.Row;

import demo.exception.CustomException;

public class TableToStreamSet {
	
	private static final Logger _log = LoggerFactory.getLogger(TableToStreamSet.class);

//	public DataStream tableToRetractStream(Datastream<Row> dsRow) throws CustomException {
//		// convert the Table into a retract DataStream of Row.
//		//   A retract stream of type X is a DataStream<Tuple2<Boolean, X>>. 
//		//   The boolean field indicates the type of the change. 
//		//   True is INSERT, false is DELETE.
//		DataStream<Tuple2<Boolean, Row>> retractStream = 
//		  tableEnv.toRetractStream(table, Row.class);
//		
//		return retractStream;
//	}
	
	public DataStream tableToStream(StreamTableEnvironment tableEnv, Table table) throws CustomException {
		// convert the Table into an append DataStream of Row by specifying the class
		DataStream<Row> dsRow = tableEnv.toAppendStream(table, Row.class);
		return dsRow;
	}
	
//	public DataStream tableToStream(Datastream<Row> dsRow, TupleTypeInfo<?> ) throws CustomException {
//
//		// convert the Table into an append DataStream of Tuple2<String, Integer> 
//		//   via a TypeInformation
//		TupleTypeInfo<Tuple2<String, Integer>> tupleType = new TupleTypeInfo<>(
//		  Types.STRING(),
//		  Types.INT());
//		DataStream<Tuple2<String, Integer>> dsTuple = 
//		  tableEnv.toAppendStream(table, tupleType);
//		return dsRow;
//	}
	
	public DataSet tableToSet(BatchTableEnvironment tableEnv, Table table) throws CustomException {
		// convert the Table into an append DataSet of Row by specifying the class
		DataSet<Row> dsRow = tableEnv.toDataSet(table, Row.class);
		return dsRow;
	}
	
	public DataSet tableToSet(BatchTableEnvironment tableEnv, Table table, TypeInformation<Row> tpe) throws CustomException {
		// convert the Table into an append DataSet of Row by specifying the class
		DataSet<Row> dsRow = tableEnv.toDataSet(table, tpe);
		return dsRow;
	}
}

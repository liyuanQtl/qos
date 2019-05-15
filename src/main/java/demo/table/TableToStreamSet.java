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

public class TableToStreamSet {
	
	private static final Logger _log = LoggerFactory.getLogger(TableToStreamSet.class);

	public DataStream tableToStream(Datastream<Row> dsRow) throws Exception {
		// convert the Table into an append DataStream of Row by specifying the class
		DataStream<Row> dsRow = tableEnv.toAppendStream(table, Row.class);

		// convert the Table into an append DataStream of Tuple2<String, Integer> 
		//   via a TypeInformation
		TupleTypeInfo<Tuple2<String, Integer>> tupleType = new TupleTypeInfo<>(
		  Types.STRING(),
		  Types.INT());
		DataStream<Tuple2<String, Integer>> dsTuple = 
		  tableEnv.toAppendStream(table, tupleType);

		// convert the Table into a retract DataStream of Row.
		//   A retract stream of type X is a DataStream<Tuple2<Boolean, X>>. 
		//   The boolean field indicates the type of the change. 
		//   True is INSERT, false is DELETE.
		DataStream<Tuple2<Boolean, Row>> retractStream = 
		  tableEnv.toRetractStream(table, Row.class);
		
		return retractStream;
	}
	
	public DataSet tableToSet(DataSet<Row> dsRow) throws Exception {
		// convert the Table into an append DataSet of Row by specifying the class
		DataSet<Row> dsRow = tableEnv.toAppendSet(table, Row.class);

		// convert the Table into an append DataSet of Tuple2<String, Integer> 
		//   via a TypeInformation
		TupleTypeInfo<Tuple2<String, Integer>> tupleType = new TupleTypeInfo<>(
		  Types.STRING(),
		  Types.INT());
		DataSet<Tuple2<String, Integer>> dsTuple = 
		  tableEnv.toAppendSet(table, tupleType);

		// convert the Table into a retract DataSet of Row.
		//   A retract set of type X is a DataSet<Tuple2<Boolean, X>>. 
		//   The boolean field indicates the type of the change. 
		//   True is INSERT, false is DELETE.
		DataSet<Tuple2<Boolean, Row>> retractSet = 
		  tableEnv.toRetractSet(table, Row.class);
		
		return retractSet;
	}
}

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
//package demo.job.batchJob;
//
//import org.apache.flink.api.java.ExecutionEnvironment;
//import org.apache.flink.api.common.functions.FlatMapFunction;
//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.api.java.DataSet;
//import org.apache.flink.util.Collector;
//
///**
// * Skeleton for a Flink Batch Job.
// *
// * <p>For a tutorial how to write a Flink batch application, check the
// * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
// *
// * <p>To package your application into a JAR file for execution,
// * change the main class in the POM.xml file to this class (simply search for 'mainClass')
// * and run 'mvn clean package' on the command line.
// */
//public class BatchJob {
//
//	public static void main(String[] args) throws Exception {
//		// ***********
//		// BATCH QUERY
//		// ***********
//		ExecutionEnvironment bEnv = ExecutionEnvironment.getExecutionEnvironment();
//		// create a TableEnvironment for batch queries
//		BatchTableEnvironment bTableEnv = TableEnvironment.getTableEnvironment(bEnv);
//		DataSet<String> lines = env.readTextFile("src/main/resources/test_batch.txt");
////        DataSet<String> lines = env.fromElements(
////                "Who's there?",
////                "I think I hear them. Stand, ho! Who's there?");
//		DataSet<Tuple2<String, Integer>> wordCounts = lines.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>(){
//			@Override
//			public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
//				for (String word : value.split("\\s")) {
//					out.collect(Tuple2.of(word, 1));
//				}
//			}
//		})
//		.groupBy(0)
//		.sum(1);
//		
//		wordCounts.print();
//
////		wordCounts.writeAsText("batch_output.txt");
//		/*
//		 * Here, you can start creating your execution plan for Flink.
//		 *
//		 * Start with getting some data from the environment, like
//		 * 	env.readTextFile(textPath);
//		 *
//		 * then, transform the resulting DataSet<String> using operations
//		 * like
//		 * 	.filter()
//		 * 	.flatMap()
//		 * 	.join()
//		 * 	.coGroup()
//		 *
//		 * and many more.
//		 * Have a look at the programming guide for the Java API:
//		 *
//		 * http://flink.apache.org/docs/latest/apis/batch/index.html
//		 *
//		 * and the examples
//		 *
//		 * http://flink.apache.org/docs/latest/apis/batch/examples.html
//		 *
//		 */
//
//		// execute program
//		env.execute("Flink Batch Java API Skeleton");
//	}
//}

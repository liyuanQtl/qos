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
//package demo.object;
//
//import demo.object.Field;
//
//public class KafkaYaml {
//	
//	private static final Logger _log = LoggerFactory.getLogger(KafkaYaml.class);
//	
//	/*firstName: "John"
//	lastName: "Doe"
//	age: 31
//	contactDetails:
//	   - type: "mobile"
//	     number: 123456789
//	   - type: "landline"
//	     number: 456786868
//	homeAddress:
//	   line: "Xyz, DEF Street"
//	   city: "City Y"
//	   state: "State Y"
//	   zip: 345657*/
//
//    private String bootstrap_servers;
//    private String zookeeper_connect;
//    private String group_id;
//    private String topic;
//    private List<Field> fields;
//    private String start_position;
//	
//	public final static Customer getCustomYaml(String yamlFilePath) {
//	    Yaml yaml = new Yaml(new Constructor(Customer.class));
//	    InputStream inputStream = this.getClass()
//	      .getClassLoader()
//	      .getResourceAsStream(yamlFilePath);
//	    Customer customer = yaml.load(inputStream);
//	    return customer;
//	}
//}

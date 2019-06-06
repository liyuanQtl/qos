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
//package demo.util;
//
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.io.InputStream;
//import org.yaml.snakeyaml.Yaml;
//
//import demo.util.YamlParser;
//import demo.common.Column;
//
//public class ParserColumns {
//	
//	private static final Logger _log = LoggerFactory.getLogger(ParserColumns.class);
//	
//	public static Map<String, Column> getColumns(String yamlFilePath) throws Exception {
//		YamlParser yamlO = new YamlParser();
//		Map<String, Object> yaml = yamlO.getYaml(yamlFilePath);
//		if (null != yaml)
//			Iterator<Entry<String, Object>> iterator = yaml.entrySet().iterator();
//			Map<String, Column> columnMap = new LinkedHashMap<String, Column>();
//			while (iterator.hasNext()) {
//			    Entry<String, Object> entry = iterator.next();
//			    String key = entry.getKey();
//			    Object values = entry.getValue();
//			    if (values instanceof List<String>) {
//				    List<String> valueList = (List<String>)values;
//				    System.out.println("values:"+valueList.get(0)+ valueList.get(1)+ valueList.get(2));
//				    Column c = new Column(valueList.get(0), valueList.get(1), valueList.get(2));
//				    columnMap.put(key, c);
//			    } else
//			    	throw new Exception("the construct of column_type error");
//			}
//			System.out.println(columnMap);
//			return columnMap;
//	}
//}

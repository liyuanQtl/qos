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
//import java.net.URL;
//import java.util.Map;
//import java.io.FileInputStream;
//
//import org.yaml.snakeyaml.Yaml;
//
//public class YamlReader {
//	
//	private static final Logger _log = LoggerFactory.getLogger(YamlReader.class);
//	
//	public final static Map<String, object> getYaml(String yamlFilePath) {
//		Yaml yaml = new Yaml();
//		InputStream inputStream = this.getClass()
//		  .getClassLoader()
//		  .getResourceAsStream(yamlFilePath);
//		Map<String, Object> obj = yaml.load(inputStream);
//		System.out.println(obj);
//		return obj;
//	}
//	
//	public final static <T> T getCustomYaml(String yamlFilePath, T customO) {
//	    Yaml yaml = new Yaml(new Constructor(customO.class));
//	    InputStream inputStream = this.getClass()
//	      .getClassLoader()
//	      .getResourceAsStream(yamlFilePath);
//	    customO = yaml.load(inputStream);
//	    return customO;
//	}
//}

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
//import java.io.FileInputStream;
//import java.net.URL;
//import java.io.IOException;
//import java.io.InputStream;
//import org.yaml.snakeyaml.Yaml;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class YamlParser {
//	
//	private static final Logger _log = LoggerFactory.getLogger(YamlParser.class);
//	
//	public Object getYaml(String yamlFilePath) {
//		Object obj = null;
//		try {
////			Yaml yaml = new Yaml();
////            URL url = YamlParser.class.getClassLoader().getResource("test.yaml");
////            if (url != null) {
////                //获取test.yaml文件中的配置数据，然后转换为obj，
////                Object obj =yaml.load(new FileInputStream(url.getFile()));
////                
//			Yaml yaml = new Yaml();
//			URL url = YamlParser.class.getClassLoader().getResource(yamlFilePath);
//			obj = yaml.load(new FileInputStream(url.getFile()));
//			System.out.println(obj);
////			try {
////				inputStream.close();
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//		} catch (Exception e) {
//			_log.error("exception:", e);
//		}
//		return obj;
//	}
//}

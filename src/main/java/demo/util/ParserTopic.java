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

package demo.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.esotericsoftware.yamlbeans.YamlReader;
//import org.yaml.snakeyaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import demo.util.YamlParser;
import demo.object.Column;
import demo.object.Topic;

public class ParserTopic {
	
	private static final Logger _log = LoggerFactory.getLogger(ParserTopic.class);
	
	public static Map<String, Topic> getTopics(String yamlFilePath) {
		YamlReader reader = null;
		try {
			reader = new YamlReader(new InputStreamReader(ParserTopic.class.getResourceAsStream(yamlFilePath)));
			Map<String, Map> yaml = (Map<String, Map>)reader.read();
			System.out.println("yaml:"+yaml);
			Iterator<Entry<String, Map>> iterator = yaml.entrySet().iterator();
			Map<String, Topic> topics = new LinkedHashMap<String, Topic>();
			while (iterator.hasNext()) {
			    Entry<String, Map> entry = iterator.next();
			    String key = entry.getKey();
			    Map values = entry.getValue();
	//		    System.out.println("key:" + key+", value:"+ values);
			    System.out.println(values.getClass());
			    List<?> fields = (List<?>)values.get("fields");
			    Map<String, Column> columns = new LinkedHashMap<String, Column>();
		    	for (Object field : fields) {
	//	    		System.out.println(field);
		     		Map<String, String> f = (Map<String, String>) field;
		    		String dis = "";
		    		if (null != f.get("discription"))
		    		    dis = f.get("discription");
		    		Column c = new Column(f.get("name").toString(), f.get("type").toString(), dis);
		    		columns.put(f.get("name").toString(), c);
		    	}
		    	String sep = " ";
		    	if (null != values.get("separate"))
		    		sep = values.get("separate").toString();
			    Topic topic = new Topic(key, values.get("type").toString(), sep, columns);
			    topics.put(key, topic);
			}
	//		System.out.println(topics);
			return topics;
		} catch (Exception e) {
			_log.error(null, e);
			return null;
		}
	}
	
//	public final static Map<String, Topic> getTopicInfo() {
//		return getTopics("/kafkaTopicsInfo.yaml");
//	}
}

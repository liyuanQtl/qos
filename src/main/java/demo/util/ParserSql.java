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

import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.object.Column;
import demo.object.TableBean;
import demo.object.AlarmBean;

public class ParserSql {
	
	private static final Logger _log = LoggerFactory.getLogger(ParserSql.class);
	
	public static Document parse(String xmlFilePath) throws DocumentException {
		InputStream inputStream = ParserSql.class.getResourceAsStream(xmlFilePath); 
//				ParserSql.getClass()
//				  .getClassLoader()
//				  .getResourceAsStream(xmlFilePath);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        return document;
    }
    
    public static Map<String, AlarmBean> read(String fileName) throws Exception {
		Document document = parse(fileName);
		Element root = document.getRootElement();
		System.out.println("root:" + root.getName());
		List<Element> alarms=root.elements();
		Map<String, AlarmBean> alarmsMap = new HashMap<String, AlarmBean> ();
		for (Element alarm : alarms) {
			String alarmName = alarm.attributeValue("name");
			Element classInfo = alarm.element("class");
			String className = classInfo.attributeValue("name");
			List<Element> tables = alarm.elements("table");
			List<TableBean> tableList = new ArrayList<TableBean> ();
			System.out.println("tables:"+tables);
			for (Element table : tables) {
				String tableName = table.attributeValue("name");
				String topicName = table.attributeValue("topic");
				List<Element> columns = table.elements("column");
				List<Column> columnList = new ArrayList<Column>();
				for (Element column : columns) {
					columnList.add(new Column(column.attributeValue("name"), column.attributeValue("type"), ""));
				}
				String sql = table.element("sql").getText();
				List<Element> params = table.elements("param");
				List<Column> paramList = new ArrayList<Column>();
				for (Element param : params) {
					paramList.add(new Column(param.attributeValue("name"), param.attributeValue("type"), ""));
				}
				tableList.add(new TableBean(tableName, topicName, sql, columnList, paramList));
			}
			alarmsMap.put(alarmName, new AlarmBean(alarmName, className, tableList));
		}
		System.out.println("alarmsMap:"+alarmsMap);
		return alarmsMap;
	}
    
    public static Map<String, AlarmBean> getSqlMap() {
    	Map<String, AlarmBean> alarmMap = null;
    	try {
    		alarmMap = read("/sql_column.xml");
    	} catch (Exception e) {
    		_log.error(null, e);
    	}
    	return alarmMap;
    }
//    public static void main(String[] args) throws Exception {
//    	ParserSql test = new ParserSql();
//    	test.read("resources/sql_column.xml");
//    }
}

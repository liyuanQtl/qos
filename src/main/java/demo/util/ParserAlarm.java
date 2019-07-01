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

import org.apache.commons.lang3.StringUtils;
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
import demo.object.TimeWindow;
import demo.object.GroupWindow;
import demo.object.OverWindow;
import demo.common.Constants;
import demo.exception.CustomException;

public class ParserAlarm {
	
	private static final Logger _log = LoggerFactory.getLogger(ParserAlarm.class);
	
	public static Document parse(String xmlFilePath) throws DocumentException {
		InputStream inputStream = ParserAlarm.class.getResourceAsStream(xmlFilePath); 
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        return document;
    }
    
    public static Map<String, AlarmBean> read(String fileName) throws CustomException {
		try {
			Document document = parse(fileName);
			Element root = document.getRootElement();
			System.out.println("root:" + root.getName());
			List<Element> alarms=root.elements();
			Map<String, AlarmBean> alarmsMap = new HashMap<String, AlarmBean> ();
			for (Element alarm : alarms) {
				String alarmName = alarm.attributeValue("name");
				Element classInfo = alarm.element("class");
				String className = classInfo.attributeValue("name");
				String tableStr = alarm.element("table").getText();
				String[] tables = StringUtils.strip(tableStr).split(",");
				alarmsMap.put(alarmName, new AlarmBean(alarmName, className, tables));
			}
			System.out.println("alarmsMap:"+alarmsMap);
			return alarmsMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(e.toString());
		}
	}
    
    public static Map<String, AlarmBean> getAlarmMap() {
    	Map<String, AlarmBean> alarmMap = null;
    	try {
    		alarmMap = read("/alarms.xml");
    	} catch (CustomException e) {
    		_log.error(null, e);
    	}
    	return alarmMap;
    }
//    public static void main(String[] args) throws CustomException {
//    	ParserSql test = new ParserSql();
//    	test.read("resources/sql_column.xml");
//    }
}

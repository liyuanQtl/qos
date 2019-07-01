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
import demo.object.Topic;
import demo.common.Constants;
import demo.exception.CustomException;

public class ParserTable {
	
	private static final Logger _log = LoggerFactory.getLogger(ParserTable.class);
	
	public static Document parse(String xmlFilePath) throws CustomException {
		try {
			InputStream inputStream = ParserTable.class.getResourceAsStream(xmlFilePath); 
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream);
	        return document;
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new CustomException("read xml error, get document exception");
		}
    }
    
    public static Map<String, TableBean> read(String fileName) throws CustomException {
		Document document = parse(fileName);
		try {
			Element root = document.getRootElement();
			System.out.println("root:" + root.getName());
			List<Element> tables = root.elements();
			System.out.println("tables:"+tables);
			Map<String, TableBean> tablesMap = new HashMap<String, TableBean> ();
			for (Element table : tables) {
				String tableName = table.attributeValue("name");
				String topicName = table.attributeValue("topic");
				Element timeAttr = table.element("timeAttr");
				String timeAttrValue = null;
				if (null != timeAttr)
				    timeAttrValue = timeAttr.getText();
				TimeWindow win = parserWindow(table.element("window"), timeAttrValue);
				String columnStr = table.element("column").getText();
				String[] columns = columnStr.split(",");
				String sql = table.element("sql").getText();
				List<Element> params = table.elements("param");
				List<Column> paramList = new ArrayList<Column>();
				for (Element param : params) {
					paramList.add(new Column(param.attributeValue("name"), param.attributeValue("type"), ""));
				}
				tablesMap.put(tableName, new TableBean(tableName, topicName, sql, columns, paramList, timeAttrValue, win));
			}
			System.out.println("tablesMap:"+tablesMap);
			return tablesMap;
		} catch (CustomException e) {
			e.printStackTrace();
			throw e;
		}
	}
    
    public static TimeWindow parserWindow(Element window, String timeAttr) throws CustomException{
    	try {
	    	if (null == window)
	    		return null;
	    	TimeWindow win = new TimeWindow();
    		String attrLower =  StringUtils.strip(timeAttr.toLowerCase());
    		if (attrLower.equals("eventtime")) {
    			win.setTimeAttr("rowtime");
    		} else if (attrLower.equals("processingtime")) {
    			win.setTimeAttr("proctime");
    		} else if (attrLower.equals("ingestiontime")) {
    			win.setTimeAttr("rowtime");
    		} else
    			throw new CustomException("time attr value error");
    	    String name = window.attributeValue("name");
    	    String text = window.getText();
    	    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(text))
    	    	throw new CustomException("the time window type error");
    	    String nameL = StringUtils.strip(name.toLowerCase());
    	    String[] ts = text.toLowerCase().split(",");
    	    if (2 > ts.length)
    	    	throw new CustomException("the time window type error");
    	    if (nameL.equals(Constants.GROUP_WINDOW)) {
    	    	win.setType(Constants.GROUP_WINDOW);
    	    	GroupWindow gw = new GroupWindow();
    	    	Map interval = parserInterval(StringUtils.strip(ts[1]));
    	    	gw.setInterval(interval);
    	    	String funName = StringUtils.strip(ts[0]);
    	    	if (funName.equals("sliding")) {
    	    		if (3 > ts.length)
    	    			throw new CustomException("window value length less than 3");
    	    		Map hopInterval = parserInterval( StringUtils.strip(ts[2]));
    	    		gw.setHopInterval(hopInterval);
    	    	} else if (funName.equals("tumbling") || funName.equals("session")) {
    	        } else
    	    		throw new CustomException("window type error");
    	    	gw.setType(funName);
    	    	win.setGroupWindow(gw);
    	    } else if (nameL.equals(Constants.OVER_WINDOW)) {
    	    	win.setType(Constants.OVER_WINDOW);
    	    	OverWindow ow = new OverWindow();
    	    	ow.setPartion( StringUtils.strip(ts[0]));
    	    	ow.setOrderBy(timeAttr);
    	    	ow.setPreceding(Integer.valueOf(StringUtils.strip(ts[1])));
    	    	win.setOverWindow(ow);
    	    } else {
    	    	throw new CustomException("the time window type error");
    	    }
    	    return win;
    	} catch (CustomException e) {
    		throw e;
    	}
    }
    
    public static Map<String, String> parserInterval(String time) throws CustomException {
    	Map<String, String> interval = new HashMap<String, String>();
    	int len = time.length();
    	if (2 > len)
    		throw new CustomException("the interval type error");
    	String value = time.substring(0, len - 1);
    	try {
    		Integer.valueOf(value);
    	} catch(NumberFormatException  e) {
    		throw new CustomException("number format error");
    	}
    	String type = time.substring(len - 1, len);
    	if (type.equals("s")) 
    		interval.put("type", "SECOND");
    	else if (type.equals("m"))
    		interval.put("type", "MINUTE");
    	else if (type.equals("h"))
    		interval.put("type", "HOUR");
    	else if (type.equals("d"))
    		interval.put("type", "DAY");
    	else
    		throw new CustomException("the interval type error");
    	interval.put("value", value);
    	return interval;
    }
    
    public static Map<String, TableBean> getTableMap() {
    	Map<String, TableBean> tableMap = null;
    	try {
    		tableMap = read("/tableInfo.xml");
    	} catch (CustomException e) {
    		_log.error(null, e);
    	}
    	return tableMap;
    }
//    public static void main(String[] args) throws CustomException {
//    	ParserSql test = new ParserSql();
//    	test.read("resources/sql_column.xml");
//    }
}

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
import demo.common.Constants.GROUP_WINDOW_ASSIGNER;
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
				Element columnEle = table.element("column");
				String columnStr = "";
				if (null != columnEle)
				    columnStr = columnEle.getText();
				String[] columns = columnStr.split(",");
				String sql = table.element("sql").getText();
				List<Element> params = table.elements("param");
				List<Column> paramList = new ArrayList<Column>();
				if (null != params) {
					for (Element param : params) {
						paramList.add(new Column(param.attributeValue("name"), param.attributeValue("type"), ""));
					}
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
	    	win = setTimeAttr(win, timeAttr)
    	    String name = window.attributeValue("name");
	    	if (StringUtils.isEmpty()) {
	    		return null;
	    	} else if (StringUtils.strip(name.toLowerCase()).equals(Constants.GROUP_WINDOW)) {
	    		win.setWindowType(Constants.GROUP_WINDOW);
	    		win.setGroupWindow(parserGroupWindow(window, timeAttr));
	    	} else if (StringUtils.strip(name.toLowerCase()).equals(Constants.OVER_WINDOW)) {
	    		win.setWindowType(Constants.OVER_WINDOW);
	    		win.setOverWindow(parserOverWindow(window, timeAttr));
	    	} else {
		    	throw new CustomException("the time window type error");
		    }
    	    return win;
    	} catch (CustomException e) {
    		throw e;
    	}
    }
    
    public TimeWindow setTimeAttr(TimeWindow window, String timeAttr) throws CustomException {
		String attrLower = StringUtils.strip(timeAttr.toLowerCase());
		if (attrLower.equals("eventtime")) {
			window.setTimeAttr("rowtime");
		} else if (attrLower.equals("processingtime")) {
			window.setTimeAttr("proctime");
		} else if (attrLower.equals("ingestiontime")) {
			window.setTimeAttr("rowtime");
		} else
			throw new CustomException("time attr value error");
		return window;
    }
    
    public GroupWindow parserGroupWindow(Element window, String timeAttr) throws CustomException {
        Element type = window.element("type");
        Element over = window.element("over");
        Element every = window.element("every");
        Element on = window.element("on");
        Element as = window.element("as");
        Element groupBy = window.element("groupBy");
        GroupWindow gw = new GroupWindow();
        if (null == type || StringUtils.isEmpty(type.getText())) {
        	throw new CustomException("the group window type empty");
        }
        String typeName = type.getText();
        if (null == groupBy || StringUtils.isEmpty(groupBy.getText()))
        	throw new CustomException("the groupBy empty");
        String groupBy = groupBy.getText();
        gw.setGroupby(groupBy);
        if (StringUtils.strip(typeName.toLowerCase()).equals("global")) {
        	gw.setType(GROUP_WINDOW_ASSIGNER.GLOBAL);
        	return gw;
        }
        if (null == over || null == on || null == as)
        	throw new CustomException("the group window fromat error");
        String overValue = over.getText();
        String onValue = on.getText();
        String asValue = on.getText();
        if (StringUtils.isEmpty(overValue) || StringUtils.isEmpty(onValue) || StringUtils.isEmpty(asValue))
        	throw new CustomException("the group window fromat error");
        gw.setOver(overValue);
        gw.setOn(onValue);
        gw.setAs(asValue);
        if (StringUtils.strip(typeName.toLowerCase()).equals("tumbling")) {
        	gw.setType(GROUP_WINDOW_ASSIGNER.TUMBLING);
        } else if (StringUtils.strip(typeName.toLowerCase()).equals("slide")) {
        	if (null == every || StringUtils.isEmpty(every.getText()))
        		throw new CustomException("the group window fromat error");
        	gw.setType(GROUP_WINDOW_ASSIGNER.SLIDE);
        	gw.setEvery(every.getText());
        } else if (StringUtils.strip(typeName.toLowerCase()).equals("session")) {
        	gw.setType(GROUP_WINDOW_ASSIGNER.SESSION);
        } else
        	throw new CustomException("the group window type error");
	    return gw;
    }
    
    public OverWindow parserOverWindow(Element window, String timeAttr) throws CustomException {
	    Element alias = window.element("alias");
	    Element partitionBy = window.element("partitionBy");
	    Element orderBy = window.element("orderBy");
	    Element preceding = window.element("preceding");
	    Element following = window.element("following");
	    if (null == alias || null == orderBy || null == preceding)
        	throw new CustomException("the over window fromat error");
	    String asValue = alias.getText();
	    String order = orderBy.getText();
	    String preValue = preceding.getText();
	    if (StringUtils.isEmpty(asValue) || StringUtils.isEmpty(order) || StringUtils.isEmpty(preValue))
        	throw new CustomException("the over window fromat error");
    	OverWindow ow = new OverWindow();
    	ow.setOrderBy(order);
    	ow.setPreceding(preValue);
    	ow.setAlias(asValue);
    	if (null != partitionBy)
    		ow.setPartionBy(partitionBy.getText());
    	if (null != following)
    		ow.setFollowing(following.getText());
    	return ow;
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

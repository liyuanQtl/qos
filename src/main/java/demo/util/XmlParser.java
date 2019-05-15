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

import java.io.FileInputStream;
import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParser {
	
	private static final Logger _log = LoggerFactory.getLogger(XmlParser.class);
	
    public Document parse(String xmlFilePath) throws DocumentException {
    	String path = this.getClass().getClassLoader().getResource("").getPath();
        SAXReader reader = new SAXReader();
        Document document = reader.read(path);
        return document;
    }
    
    public void treeWalk(Document document) {
        treeWalk(document.getRootElement());
    }

    public void treeWalk(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                treeWalk((Element) node);
            }
            else {
                // do something…
            	Map<String, Object> nodeMap = new HashMap<String, Object>();
//            	String nodeValue = node.valueOf("@name");
//            	System.out.println("node:"+node);
//            	System.out.println("nodeValue:"+node.valueOf("@name"));
            }
        }
    }
    
//    public void fromOcean(String node, String data) {
//		try {
//			Document doc = DocumentHelper.parseText(data);
//			Element root = doc.getRootElement();
//			for (Iterator<?> iterator = root.elementIterator(); iterator.hasNext();) {
//				Element element = (Element) iterator.next();
//				String appName = element.attributeValue("app-name");
//				String meta = element.attributeValue("meta");
//				// handler下的元素共用一个appName和meta
//				if ("handler".equals(element.getName())) {
//					List<Element> list = element.elements();
//					for (Element ele : list) {
//						addHandler(appName, meta, ele);
//					}
//				} else {
//					addHandler(appName, meta, element);
//				}
//			}
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//    }
    
    public static void main(String[] args) throws Exception {
    	XmlParser test = new XmlParser();
    	Document doc = test.parse("operators.xml");
    	test.treeWalk(doc);
    }
}

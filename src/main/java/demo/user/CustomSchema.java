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

package demo.user;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONException;
import java.util.Iterator;
//import java.util.LinkedHashMap;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.SqlTimeTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.types.Row;
import org.apache.flink.table.api.TableNotExistException;

import demo.object.Topic;
import demo.object.Column;

public class CustomSchema implements DeserializationSchema<Row> {
	
	private static final Logger _log = LoggerFactory.getLogger(CustomSchema.class);
	private String dataType = "json";
	private String separate = " ";
	private Map<String, Column> fields = null;
	
	private TypeInformation<?> getTypeInfo(String type) throws TableNotExistException {
//	     String type = schema.getType();
	    if (type.toLowerCase().equals("string")) {
	        return BasicTypeInfo.STRING_TYPE_INFO;
	    } else if (type.toLowerCase().equals("boolean")) {
	        return BasicTypeInfo.BOOLEAN_TYPE_INFO;
	    } else if (type.toLowerCase().equals("byte")) {
	        return BasicTypeInfo.BYTE_TYPE_INFO;
	    } else if (type.toLowerCase().equals("short")) {
	        return BasicTypeInfo.SHORT_TYPE_INFO;
	    } else if (type.toLowerCase().equals("int")) {
	        return BasicTypeInfo.INT_TYPE_INFO;
	    } else if (type.toLowerCase().equals("long")) {
	        return BasicTypeInfo.LONG_TYPE_INFO;
	    } else if (type.toLowerCase().equals("float")) {
	        return BasicTypeInfo.FLOAT_TYPE_INFO;
	    } else if (type.toLowerCase().equals("double")) {
	        return BasicTypeInfo.DOUBLE_TYPE_INFO;
	    } else if (type.toLowerCase().equals("char")) {
	        return BasicTypeInfo.CHAR_TYPE_INFO;
	    } else if (type.toLowerCase().equals("void")) {
	        return BasicTypeInfo.VOID_TYPE_INFO;
	    } else if (type.toLowerCase().equals("biginteger")) {
	        return BasicTypeInfo.BIG_INT_TYPE_INFO;
	    } else if (type.toLowerCase().equals("bigdecimal")) {
	        return BasicTypeInfo.BIG_DEC_TYPE_INFO;
	    } else if (type.toLowerCase().equals("date")) {
	        return SqlTimeTypeInfo.DATE;
	    } else if (type.toLowerCase().equals("timestamp")) {
	        return SqlTimeTypeInfo.TIMESTAMP;
	    } else if (type.toLowerCase().equals("time")) {
	        return SqlTimeTypeInfo.TIME;
//	        } else if (type.toLowerCase().equals("row")) {
//	            //目前复杂的支持row,其它的待扩展
//	            List<AthenaxJSONSchema> schemaList = schema.getSchema();
//	            List<String> nameList = new ArrayList<String>();
//	            List<TypeInformation<?>> typeInformationList = new ArrayList<TypeInformation<?>>();
//	            //有数据就继续处理
//	            if (null != schemaList) {
//	                for (AthenaxJSONSchema childSchema : schemaList) {
//	                    nameList.add(childSchema.getKey());
//	                    typeInformationList.add(getTypeInfo(childSchema));
//	                }
//	            }
//	            //构造返回结果
//	            String[] nameArray = new String[nameList.size()];
//	            nameList.toArray(nameArray);
//	            TypeInformation<?>[] typeInformationArray = new TypeInformation<?>[typeInformationList
//	                .size()];
//	            typeInformationList.toArray(typeInformationArray);
//	            //return new RowTypeInfo();
//	            return Types.ROW(nameArray, typeInformationArray);
	    } else {
	        String errorMsg = "fail to find TypeInformation for type[" + type + "]";
	        _log.error(errorMsg);
	        throw new TableNotExistException(type, type);
	    }
	}
	
	private Object convertType(String value, String type) {
//		System.out.println("convert value:"+value+", type:"+type);
	    if (type.toLowerCase().equals("boolean")) {
	        return Boolean.valueOf(value);
	    } else if (type.toLowerCase().equals("byte")) {
	        return Byte.valueOf(value);
	    } else if (type.toLowerCase().equals("short")) {
	        return Short.valueOf(value);
	    } else if (type.toLowerCase().equals("int")) {
	        return Integer.valueOf(value);
	    } else if (type.toLowerCase().equals("long")) {
	        return Long.valueOf(value);
	    } else if (type.toLowerCase().equals("float")) {
	        return Float.valueOf(value);
	    } else if (type.toLowerCase().equals("double")) {
	        return Double.valueOf(value);
	    } else if (type.toLowerCase().equals("biginteger")) {
	        return new BigInteger(value);
	    } else if (type.toLowerCase().equals("bigdecimal")) {
	        return new BigDecimal(value);
	    } else if (type.toLowerCase().equals("timestamp")) {
	        return Timestamp.valueOf(value);
	    }
	    return value;
	}
	
	public RowTypeInfo getRowTypeInfo() {
		int len = fields.size();
		String[] names = new String[len];
		TypeInformation<?>[] types = new TypeInformation<?>[len];
		Iterator<Entry<String, Column>> iterator = fields.entrySet().iterator();
		int i = 0;
		while (iterator.hasNext()) {
		    Entry<String, Column> entry = iterator.next();
		    String key = entry.getKey();
		    Column column = entry.getValue();
		    names[i] = key;
		    types[i++] = getTypeInfo(column.getType());
		}
		RowTypeInfo typeInfo = new RowTypeInfo(types, names);
//		System.out.println("rowtypeInfo:"+typeInfo);
		return typeInfo;
	}
	
	public CustomSchema(Topic topic) {
		this.dataType = topic.getType();
		this.fields = topic.getFields();
		if (null != topic.getSeparate())
			separate = topic.getSeparate();
	}
	
    @Override
    public Row deserialize(byte[] bytes) {
    	String s = new String(bytes);
        int len = fields.size();
        Row row = new Row(len);
		Iterator<Entry<String, Column>> iterator = fields.entrySet().iterator();
		int i = 0;
		if ("json".equals(dataType)) {
			JSONObject jo = JSONObject.parseObject(s);
			while (iterator.hasNext()) {
			    Entry<String, Column> entry = iterator.next();
			    String key = entry.getKey();
			    String type = entry.getValue().getType();
			    row.setField(i, convertType(jo.getString(key), type));
//				System.out.print("key:"+key+", joValue:"+jo.getString(key)+", type:"+type);
				i++;
			}
		} else {
			String[] pieces = s.split(separate);
			while (iterator.hasNext()) {
			    Entry<String, Column> entry = iterator.next();
			    String type = entry.getValue().getType();
			    row.setField(i, convertType(pieces[i], type));
			    i++;
			}
		}
//		System.out.println("row:"+row);
        return row;
    }

//    @Override
//    public byte[] serialize(Flow flow) {
//    	return String.format("%s,%dl,%dl,%dl,%dl", flow.getDomain(), flow.getSend(), flow.getRecv(), flow.getMsec(), flow.getDate().getTime()).getBytes();
//    }
	
	@Override
	public boolean isEndOfStream(Row nextElement) {
	    return false;
	}
    @Override
    public TypeInformation<Row> getProducedType() {
        return getRowTypeInfo();
    }
}

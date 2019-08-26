package hs.bm.util;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import cn.org.hsxx.bean.TestPoint;
import cn.org.hsxx.dao.TestPointDao;
import hs.bm.dao.BasicDataDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IDtool
{
	public static String getUUID(){
		UUID uuid = java.util.UUID.randomUUID();
		String uid = uuid.toString();
		return uid;
		
	}
	public static void main(String[] args)
	{
		String id = getUUID();
		//System.out.println(id);
		insertByBimJson("GHT");
	}
	public static void insertByBimJson(String fileName){
		File file = new File("E:\\bim\\BimMore\\"+fileName+".json");
	    JSONArray jsonArray = new JSONArray();
		try {
		    String input = org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(input);
            if (jsonObject != null) {
            	jsonArray = jsonObject.getJSONArray("sensorDataAry");
            }
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
				JSONObject jsonObject2 = (JSONObject) iterator.next();
				System.out.println(jsonObject2);
				TestPoint tp = new TestPoint();
				tp.setId(jsonObject2.getString("uuid"));
				tp.setIf_jihuo("1");
				tp.setTd_code(jsonObject2.getString("sensorID"));
				String sensorType = jsonObject2.getString("sensorType");
				if("钢应变".equals(sensorType)||"混凝土应变".equals(sensorType)){
					sensorType="应变";
				}
				if("动位移".equals(sensorType)||"静位移".equals(sensorType)){
					sensorType="位移";
				}
				tp.setCd_type_id(BasicDataDao.getInstance().getCdFTypeIdByName(sensorType));
				tp.setCd_describe(jsonObject2.getString("sensorDes"));
				tp.setModelID(jsonObject2.getString("modelID"));
				tp.setBridge_id(getBridgeIdByFileName(fileName));
				TestPointDao.getInstance().insertTestPoint(tp );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getBridgeIdByFileName(String fileName){
		if("YCD".equals(fileName)){
			return "G15320902L0020"; //盐城东互通主线桥
		}else if("LSH".equals(fileName)){
			return "G15320682L0130"; //烈士河大桥
		}else if("GHT".equals(fileName)){
			return "G15320921L0010"; //灌河特大桥
		}else if("XSH".equals(fileName)){
			return "G15320705L0010";//新沭河大桥
		}else if("SBGG".equals(fileName)){
			return "9dfbf3236c7844d189dc34ed95d1618c";
		}else if("XYH".equals(fileName)){
			return "6f78200f27d54b5394ba8200162cb917";
		}else{
			return "";
		}
		
	}
}

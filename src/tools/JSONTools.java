package tools;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;




public class JSONTools {

	public static String toJSON(Object object) {
		// TODO Auto-generated method stub
		return JSONObject.fromObject(object).toString();
	}
	
	public static <T> T jsonToObject(String str,Class<T> z) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=JSONObject.fromObject(str);
		return (T)JSONObject.toBean(jsonObject,z);
	}
	
	public static void main(String[] args) {
		Map<String,String> tt=new HashMap<String,String>();
		tt.put("a", "value");
		tt.put("b", "value");
		System.err.println(toJSON(tt));


		BB3 b=new BB3();
		System.err.println(toJSON(b));
		
		String ajson="{\"de\":45,\"name\":\"dddd\"}";
		BB3 dee=jsonToObject(ajson, BB3.class);
		System.err.println(dee.getDe());
	}
	

}



package web;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.free.restarc.conf.MQTTConf;

import tools.JSONTools;
import tools.MQTTTools;

import mqtt.SimpleMQTTClient;
import mqtt.SimpleMQTTClientOptions;


import com.ibm.mqtt.MqttException;


@Path("/push")
public class PushResource {
	static Log log = LogFactory.getLog(PushResource.class);
	private static SimpleMQTTClient simpleMQTTClient=null;
	static{
		simpleMQTTClient=MQTTTools.getInstance(MQTTConf.loadConfig(),log);
		System.out.println("static executed!");
		System.out.println("static executed!1111111111");;;;
	}
	
	

	
	@POST
	@Produces("text/html; charset=utf-8")
	public String pushMessages(@FormParam("t") String t,@FormParam("m") String m){
		//System.err.println(representation);
		Map<String,String> tt=new HashMap<String,String>();
		log.debug("Client want to push message. "+"topic is "+t+" , message is "+m);
		try {
			tt.put("messageID", simpleMQTTClient.publish(t, m)+"");
			tt.put("result", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			tt.put("result", "error");
			tt.put("cause", "the pushServer cann't connected to the broker!");
			tt.put("exception", e.getLocalizedMessage());
		}
		
		return  JSONTools.toJSON(tt);
	}

}

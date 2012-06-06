package org.free.restarc.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.free.restarc.tools.XmlUtil;

import mqtt.SimpleMQTTClientOptions;

public class MQTTConf {
	
	private static Log log = LogFactory.getLog(MQTTConf.class);
	private static final String CONFIG_FILE="mqtt.xml";
	
	public static SimpleMQTTClientOptions  loadConfig(){
		SimpleMQTTClientOptions simpleMQTTClientOptionss=new SimpleMQTTClientOptions();
		try {
			simpleMQTTClientOptionss.cleanSession=
					loadSpecialConfigValue( "//mqtt/cleanSession/@value",true).equals("true")?true:false;
			simpleMQTTClientOptionss.clientId=
					loadSpecialConfigValue( "//mqtt/clientId/@value",true);
			simpleMQTTClientOptionss.commonQoS=
					Integer.parseInt(loadSpecialConfigValue( "//mqtt/commonQoS/@value",true));
			simpleMQTTClientOptionss.ip=
					loadSpecialConfigValue( "//mqtt/ip/@value",true);
			simpleMQTTClientOptionss.keepAlive=
					Short.parseShort(loadSpecialConfigValue( "//mqtt/keepAlive/@value",true));
			simpleMQTTClientOptionss.persistDir=
					loadSpecialConfigValue( "//mqtt/persistDir/@value",true);
			simpleMQTTClientOptionss.persistEnable=
					loadSpecialConfigValue( "//mqtt/persistEnable/@value",true).equals("true")?true:false;
			simpleMQTTClientOptionss.port=
					Integer.parseInt(loadSpecialConfigValue( "//mqtt/port/@value",true));
			simpleMQTTClientOptionss.retryInterval=
					Short.parseShort(loadSpecialConfigValue( "//mqtt/retryInterval/@value",true));
		} catch (Throwable e) {
			log.info("Loading security config error!", e);
			
		}
		return simpleMQTTClientOptionss;
	}
	
	private static String loadSpecialConfigValue(String xpathStr,boolean canNotBeNull) throws Throwable{
		String theStr= XmlUtil.getSingleValueFromClassPathFile(CONFIG_FILE, xpathStr);
		if(canNotBeNull){
			if(theStr==null || theStr.trim().length()==0)
				throw new Exception(xpathStr+" error!");
		}
		return theStr.trim();
	}
}

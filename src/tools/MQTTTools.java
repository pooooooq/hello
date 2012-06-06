package tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import web.PushResource;

import com.ibm.mqtt.MqttException;

import mqtt.SimpleMQTTClient;
import mqtt.SimpleMQTTClientOptions;

public class MQTTTools {
	static Log log = LogFactory.getLog(MQTTTools.class);
	
	public static SimpleMQTTClient getInstance(SimpleMQTTClientOptions options,Log log) {
		// TODO Auto-generated method stub
		boolean isConnected=false;
		SimpleMQTTClient simpleMQTTClient=new SimpleMQTTClient(options);
		while(!isConnected){
			try {
				//may not success
				simpleMQTTClient.connect();
				isConnected=true;
			} catch (MqttException e1) {
				log.info("MQTTClient("+options.clientId+") cann't connected to broker,retry......",e1);
			}
		}
		return simpleMQTTClient;
	}

	
	public static void main(String[] args) {
		for(int i=0;i<10000;i++){
			SimpleMQTTClientOptions options =new SimpleMQTTClientOptions();
			options.clientId+=i;
			getInstance(options, log);
		}
	}
	
}

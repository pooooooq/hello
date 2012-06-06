package mqtt;
import com.ibm.mqtt.MqttClient;


public class SimpleMQTTClientOptions {
    public boolean  cleanSession=false;
    public boolean  persistEnable=true;
    public String 	clientId="MQTT_abc";
    public short 	keepAlive=30;
    public short 	retryInterval=10;
    public String 	persistDir=".";
    public int  	commonQoS=2;
    public String	ip="127.0.0.1";
    public int		port=1883;
    
    public static final String TCP_ID="tcp://";
    public String getConnStr(){
    	return TCP_ID + ip + ":" + port;
    }
}

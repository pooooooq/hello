package mqtt;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttAdvancedCallback;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttPersistence;


public class SimpleMQTTClient implements MqttAdvancedCallback {

	Log log = LogFactory.getLog(SimpleMQTTClient.class);
	private IMessageReceved iMessageReceved=null;
	private SimpleMQTTClientOptions options=null;
	private IMqttClient 			mqtt = null;
	private boolean 				connected = false;
	private Object    				connLostWait = new Object();
	
	public SimpleMQTTClient(SimpleMQTTClientOptions options) {
		this.options=options;
	}
	
    public void connect( ) throws MqttException {
    	if(options==null) return;
    	if ( mqtt == null ) {
			MqttPersistence persistence = null;
			if ( options.persistEnable ) {
				persistence = new MqttFilePersistence( options.persistDir );
			}	
        	mqtt = MqttClient.createMqttClient( options.getConnStr(), persistence );
    	    mqtt.registerAdvancedHandler( this );
		}	
        // Set the retry interval for the connection
        mqtt.setRetry(options.retryInterval);
       	mqtt.connect(options.clientId,options.cleanSession, options.keepAlive);
        connected = true;
        log.info("SimpleMQTTClient("+options.clientId+") connected to " + mqtt.getConnection());
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public int publish(String topic, String message) throws Exception{
		int i=-1;
    	if ( connected ) {
    		try {
    		    i=mqtt.publish( topic, message.getBytes(Charset.forName("utf-8")), options.commonQoS, false );
    		    log.debug("SimpleMQTTClient publishing : MessageID="+i+",Message="+topic+":"+message);
    		} catch ( MqttException ex ) {
    			ex.printStackTrace();
        		throw ex;
    		}	
    	} else {
    		throw new Exception( "SimpleMQTTClient client not connected" );
    	}
		return i;
	}

	@Override
	public void connectionLost() throws Exception {
    	int rc = -1;
   	    synchronized(this) { // Grab the log synchronisation lock
     		log.info( "SimpleMQTTClient Connection Lost!....Reconnecting to " + mqtt.getConnection() );
   	    }	
    	try {
    		// While we have failed to reconnect and disconnect hasn't
    		// been called by another thread retry to connect
    		while ( (rc == -1) &&  connected ) {
           		try {
           			synchronized(connLostWait) {
               			connLostWait.wait(10000);
           			}
           		} catch (InterruptedException iex) {
       		    }		

        	    synchronized(this) { // Grab the log synchronisation lock
        	    	if ( connected ) {
        	    		log.info( "SimpleMQTTClient reconnecting......" );
        	    		try {
        	    			connect();
        	    			rc = 0;
        	    		} catch (MqttException mqte) {
        	    			rc = -1;
        	    		}		
        	    		if ( rc == -1 ) {
        	    			log.info( "SimpleMQTTClient reconnecting......failed" );
        	    		} else {
        	    			log.info( "SimpleMQTTClient reconnecting......success !" );
        	    		}		
        	    	}
        	    }
    		}	
    		// Remove title text once we have reconnected
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		disconnect();
    		throw ex;
    	} 
	}

	@Override
	public void publishArrived(String topic, byte[] data, int QoS, boolean retained)
			throws Exception {
		// TODO Auto-generated method stub
		iMessageReceved.processMessage(topic, new String(data));
	}

	@Override
	public void published(int messageID) {
		// TODO Auto-generated method stub
		log.debug("SimpleMQTTClient published : MessageID="+messageID);
	}

	@Override
	public void subscribed(int arg0, byte[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribed(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean outstanding(int messageID){
		return connected==true?mqtt.outstanding(messageID):false;
	}
	
    public void disconnect() {
		connected = false;
        // Notify connectionLost to give up. It may be running..
		synchronized(connLostWait) {
   			connLostWait.notify();
		}
		// Disconnect from the broker
		if ( mqtt != null ) {
  			try {
  				mqtt.disconnect();
  				mqtt.terminate();
   			} catch ( Exception ex ) {
   				ex.printStackTrace();
   			}	 
   		}		
   	    synchronized(this) { // Grab the log synchronisation lock
   	    	log.info("SimpleMQTTClient disconnected and terminated!" );
		}	
    }

	public void subscription(String topic, IMessageReceved iMessageReceved, boolean sub) {
    	if ( connected ) {
    		try {
        	    synchronized(this) { // Grab the log synchronisation lock
                  if ( sub ) {
                	  log.debug( "SimpleMQTTClient SUBSCRIBE,        TOPIC:" + topic + ", Requested QoS:" + 2 );
                  } else {
                	  log.debug( "SimpleMQTTClient UNSUBSCRIBE,      TOPIC:" + topic );
                  }	  
        	    }
        	    String[] theseTopics = new String[1];
    		    int[] theseQoS = new int[1];
    		    
        	    theseTopics[0] = topic;
    		    theseQoS[0] = 2;
        	    this.iMessageReceved=iMessageReceved;
                if ( sub ) {
        		    mqtt.subscribe( theseTopics, theseQoS );
                } else {
                    mqtt.unsubscribe( theseTopics );
                }	  
    		  
    		} catch ( Exception ex ) {
    			log.debug( "SimpleMQTTClient Subscription Exception:");
    		}	
    	} else {
    		log.debug( "SimpleMQTTClient not connected !" );
    	}	
	}


}

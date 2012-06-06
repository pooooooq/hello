package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

public class HMac {
	public static byte[] decryptBASE64(String key) throws Exception {
       // return (new BASE64Decoder()).decodeBuffer(key);
		return null;
    }
	public static byte[] InputStreamToByte(InputStream is) throws IOException {   
	    ByteArrayOutputStream bytestream = new ByteArrayOutputStream();   
	   int ch;   
	   while ((ch = is.read()) != -1) {   
	     bytestream.write(ch);   
	    }   
	   byte imgdata[] = bytestream.toByteArray();   
	    bytestream.close();   
	   return imgdata;   
	}  
	
	public static String encryptBASE64(byte[] key) throws Exception {
		          //return (new BASE64Encoder()).encodeBuffer(key);
		return null;
		      }
	
	
	public static byte[] addTwoByteArray(byte[] abyte1,byte[] abyte2){
		int t=(abyte2==null)?0:abyte2.length;
		byte[]   abyte   =   new   byte[abyte1.length   +  t ]; 
		System.arraycopy(abyte1,0,abyte,0,abyte1.length); 
		if(abyte2 !=null) 
		System.arraycopy(abyte2,0,abyte,abyte1.length,abyte2.length);
		return abyte;
	}
	


	
	public static byte[] hMac(HashMap<String, Object> parm) throws Exception{
		Object[]   key   =  parm.keySet().toArray(); 
		
		byte[]	resultByte = null;
        Arrays.sort(key);  
        for   (int   i   =   0;   i   <   key.length;   i++)   { 
                //System.out.println(parm.get(key[i]));
                String k=(String) key[i];Object v=parm.get(key[i]);
                if(k.equals("hmac")) continue;
                if(v instanceof InputStream){
                	resultByte=addTwoByteArray(  InputStreamToByte((InputStream)v),resultByte  );
                	resultByte=addTwoByteArray(  resultByte,k.getBytes(Charset.forName("utf-8"))  );
                }else{
                	resultByte=addTwoByteArray(  (k+(String)v).getBytes(Charset.forName("utf-8")),resultByte  );
                }
        } 
        return encryptHMAC(resultByte,"thePKey");
	}
	
	public static String hMac64Str(byte[] b){
		try {
			return encryptBASE64(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		         SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), "HmacSHA1");
		         Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		         mac.init(secretKey);
		         return mac.doFinal(data);
	}
}

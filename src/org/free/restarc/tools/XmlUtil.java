package org.free.restarc.tools;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class XmlUtil {
	private static DocumentBuilderFactory domfac=null;
	private static DocumentBuilder dombuilder =null;
	private static XPathFactory factoryXpah = null;
    private static XPath xpath = null;
	static{
		domfac = DocumentBuilderFactory.newInstance();
		factoryXpah = XPathFactory.newInstance();
		xpath = factoryXpah.newXPath();
		try {
			dombuilder = domfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static Node getSingleNode(InputStream is,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		Node result=null;
		Document doc=null;
		doc = dombuilder.parse(is);
		//"//db/@dbType"
		result=(Node) xpath.compile(xpathStr).evaluate(doc,XPathConstants.NODE);
		return result;
	}
	
	public static Node getSingleNode(String fileName,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		Node result=null;
		Document doc=null;
		doc = dombuilder.parse(fileName);
		//"//db/@dbType"
		result=(Node) xpath.compile(xpathStr).evaluate(doc,XPathConstants.NODE);
		return result;
	}
	
	public static String getSingleValueFromFile(String fileName,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		InputStream is=new FileInputStream(new File(fileName));
		return getSingleValue(is, xpathStr);
	}
	
	
	public static String getSingleValueFromClassPathFile(String classPath,String xpathStr) throws XPathExpressionException, SAXException, IOException{
		String value=null;
		
//		String absoluteFile=null;
//		URL uri=XmlUtil.class.getClassLoader().getResource(classPath);
//			//ClassLoader.getSystemResource(classPath);
//		absoluteFile=uri.getFile();
		InputStream is=null;
		
			is= XmlUtil.class.getClassLoader().getResourceAsStream(classPath);//new FileInputStream(classPath);
			value= getSingleValue(is, xpathStr);//getSingleValueFromFile(absoluteFile,xpathStr );//
		
		return value;
	}
	
	
	public static String getSingleValueFromString(String xmlStr,String xpathStr) throws XPathExpressionException, SAXException, IOException{
		InputStream is =new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
		return getSingleValue(is, xpathStr);
	}
	
	public static String[] getMultiValue(String xmlStr,String xpathStr) throws XPathExpressionException, SAXException, IOException{
		if(xmlStr==null) return null;
		InputStream is =new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
		return getMultiValue(is, xpathStr);
	}
	
	/***
	 * 
	 * @param is
	 * @param xpathStr
	 * @return
	 * if is is null , return null<br>
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static String[] getMultiValue(InputStream is,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		String[] result=null;
		Document doc=null;
		if(is==null) return result;
		doc = dombuilder.parse(is);
		Object obj=xpath.compile(xpathStr).evaluate(doc,XPathConstants.NODESET);
		if(obj!=null){
			NodeList nodes = (NodeList)obj;
			result=new String[nodes.getLength()];
			for(int i=0;i<nodes.getLength();i++)
				result[i]=nodes.item(i).getNodeValue();
		}
		return result;
	}
	
	public static Node[] getMultiNode(InputStream is,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		Node[] result=null;
		Document doc=null;
		if(is==null) return result;
		doc = dombuilder.parse(is);
		Object obj=xpath.compile(xpathStr).evaluate(doc,XPathConstants.NODESET);
		if(obj!=null){
			NodeList nodes = (NodeList)obj;
			result=new Node[nodes.getLength()];
			for(int i=0;i<nodes.getLength();i++)
				result[i]=nodes.item(i);
		}
		return result;
	}
	
	
	public static String[] getMultiValueFromClassPathFile(String classPath,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		URL uri=XmlUtil.class.getClassLoader().getResource(classPath);
		InputStream is=new FileInputStream(new File(uri.getFile()));
		return getMultiValue(is, xpathStr);
	}
	
	
	public static Node[] getMultiNodeFromFile(String fileName,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		InputStream is=new FileInputStream(new File(fileName));
		return getMultiNode(is, xpathStr);
	}
	
	public static boolean nodeExist(InputStream is,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		boolean result=false;
		Document doc=null;
		if(is==null) return result;
		doc = dombuilder.parse(is);
		Object obj=xpath.compile(xpathStr).evaluate(doc,XPathConstants.NODESET);
		if(obj!=null){
			NodeList nodes = (NodeList)obj;
			if(nodes.getLength()>0){
				result=true;
			}
		}
		return result;
	}
	
	public static boolean nodeExist(String xmlStr,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		InputStream is =new ByteArrayInputStream(xmlStr.getBytes("utf-8"));
		return nodeExist(is, xpathStr);
	}
	
	public static String[] getMultiValueFromFile(String fileName,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		InputStream is=new FileInputStream(new File(fileName));
		return getMultiValue(is, xpathStr);
	}
	
	/***
	 * 
	 * @param is
	 * @param xpathStr
	 * @return
	 * if is is null , return null<br>
	 * only the fist value will be returned
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static String getSingleValue(InputStream is,String xpathStr) throws SAXException, IOException, XPathExpressionException{
		String result=null;
		Document doc=null;
		if(is==null) return result;
		if(xpathStr==null||xpathStr.trim().length()==0) return result;
		doc = dombuilder.parse(is);
		//"//db/@dbType"
		result=xpath.compile(xpathStr).evaluate(doc);
		return result;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*
		Map test1=new HashMap<String, String>();
		test1.put("name", "caonima");
		XmlUtil.addNodeToXml("/security/test2", 
				XmlUtil.class.getClassLoader().getSystemResource("security.xml").getFile(), 
				"test21111", "ddd", test1);
		*/
		System.err.println(XmlUtil.getSingleValueFromClassPathFile("security.xml", "/sss/text()"));
		
		/*
		Map test1=new HashMap<String, String>();
		test1.put("name", "caonimaaaaaaaaaa");
		test1.put("name3", "caonimeeea");
		XmlUtil.updateNodeToXml("/security/test2/test21111", 
				XmlUtil.class.getClassLoader().getSystemResource("security.xml").getFile(), 
				 "d222sassddsdsfdd", test1);
		*/
		
		/*
		Map test1=new HashMap<String, String>();
		test1.put("name", "caonimaaaaaaaaaa");
		test1.put("name3", "caonimeeea");
		XmlUtil.deleteNodeFromXml("/security/test2", 
				XmlUtil.class.getClassLoader().getSystemResource("security.xml").getFile());
		*/
		
	}
	
	public static void addNodeToXml(String nodeParentXpathStr,String xmlFilePath,String nodeName,String value,Map<String,String> attr) throws Exception{
		Document doc=null;
		if(xmlFilePath==null || nodeParentXpathStr==null || nodeName==null || nodeParentXpathStr==null) 
			throw new Exception("some parameters can not be null!");
		doc = dombuilder.parse(new File(xmlFilePath));
		Node pNode=(Node)xpath.compile(nodeParentXpathStr).evaluate(doc,XPathConstants.NODE);
		if(pNode==null) throw new Exception("can not find the node specified in nodeParentXpathStr!");;
		
		Element newNode = doc.createElement(nodeName);  
		newNode.setTextContent(value);  
		if(attr!=null && !attr.isEmpty()){
			for(String key : attr.keySet())
				newNode.setAttribute(key, attr.get(key));
		}
		
		pNode.appendChild(newNode);
		writeToXmlFile(doc,xmlFilePath);
		
	}
	
	public static void updateNodeToXml(String nodeXpathStr,String xmlFilePath,String value,Map<String,String> attr) throws Exception{
		Document doc=null;
		if(xmlFilePath==null || nodeXpathStr==null ) throw new Exception("some parameters can not be null!");
		doc = dombuilder.parse(new File(xmlFilePath));
		Node pNode=(Node)xpath.compile(nodeXpathStr).evaluate(doc,XPathConstants.NODE);
		if(pNode==null) throw new Exception("can not find the node specified in nodeXpathStr!");;
		pNode.setTextContent(value);
		
		if(attr!=null && !attr.isEmpty()){
			for(String key : attr.keySet())
				((Element)pNode).setAttribute(key, attr.get(key));
		}
		
		writeToXmlFile(doc,xmlFilePath);
	}
	
	public static void deleteNodeFromXml(String nodeXpathStr,String xmlFilePath) throws Exception{
		Document doc=null;
		if(xmlFilePath==null || nodeXpathStr==null ) throw new Exception("some parameters can not be null!");
		doc = dombuilder.parse(new File(xmlFilePath));
		Node pNode=(Node)xpath.compile(nodeXpathStr).evaluate(doc,XPathConstants.NODE);
		if(pNode==null) throw new Exception("can not find the node specified in nodeXpathStr!");;

		pNode.getParentNode().removeChild(pNode);
	
		writeToXmlFile(doc,xmlFilePath);
	}
	
	private static void writeToXmlFile(Document doc,String xmlFilePath) throws FileNotFoundException, TransformerException{
		 TransformerFactory tffactory = TransformerFactory.newInstance();  
	     Transformer tf = tffactory.newTransformer();  
	     tf.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xmlFilePath)));  
	}
	

}

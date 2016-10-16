package com.bnade.wow.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bnade.util.HttpClient;
import com.bnade.wow.client.model.Item;
import com.bnade.wow.client.model.XItem;
import com.bnade.wow.client.model.XWowHead;

public class WowHeadClient implements WowAPI {
	
	private static final String HOST = "http://@region.wowhead.com";
	
	private String region = "cn";	
	private HttpClient httpClient;
	
	public WowHeadClient() {
		httpClient = new HttpClient();
	}
	 
	@Override
	public Item getItem(int id) throws WowClientException {
		String url = HOST.replace("@region", region) + "/item=" + id + "&xml";
		try {
			return parserXml(id, url);
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new WowClientException(e);
		}		
	}
	
	private Item parserXml(int id, String url) throws ParserConfigurationException, SAXException, IOException {
		/*
		 * 	解析XML文件时，会碰到程序发生以下一些异常信息：
			在 CDATA 节中找到无效的 XML 字符 (Unicode: 0x1f)。			
			或者：			
			An invalid XML character (Unicode: 0x1f) was found in the CDATA section.			
			这些错误的发生是由于一些不可见的特殊字符的存在，而这些字符对于XML文件来说又是非法的，所以XML解析器在解析时会发生异常，官方定义了XML的无效字符分为三段：			
			0x00 - 0x08			
			0x0b - 0x0c			
			0x0e - 0x1f			
			解决方法是：在解析之前先把字符串中的这些非法字符过滤掉即可， 不会影响原来文本的内容。			
			即：string.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "")			
			另外：这些字符即使放在CDATA中仍然解析不了，所以最好的办法是过滤掉。 
		 */
		String xml = httpClient.get(url).replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
//		System.out.println(xml);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        Element root = doc.getDocumentElement();
        NodeList itemList = root.getChildNodes();
        if (itemList.getLength() > 0) {
        	Node itemNode = itemList.item(0);  
        	NodeList itemAttrs = itemNode.getChildNodes();        	
        	Item item = new Item();
        	item.setId(id);
        	item.setName(getNodeText("name", itemAttrs));
        	item.setLevel(Integer.valueOf(getNodeText("level", itemAttrs)));
        	item.setQuality(Integer.valueOf(getAttrValue("quality", "id", itemAttrs)));
        	item.setItemClass(Integer.valueOf(getAttrValue("class", "id", itemAttrs)));
        	item.setSubClass(Integer.valueOf(getAttrValue("subclass", "id", itemAttrs)));
        	item.setIcon(getNodeText("icon", itemAttrs));
        	item.setInventorySlot(Integer.valueOf(getAttrValue("inventorySlot", "id", itemAttrs)));
        	return item;
        }
		return null;
	}	
	
	public XItem getItem2(int id) throws WowClientException {		
		try {
			String url = HOST.replace("@region", region) + "/item=" + id + "&xml";
			String xml = httpClient.get(url).replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "");
//			System.out.println(xml);
			return converyToJavaBean(xml, XWowHead.class).getItem();
		} catch (IOException e) {
			throw new WowClientException(e);
		}		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T converyToJavaBean(String xml, Class<T> c) {
		try {
			return (T)JAXBContext.newInstance(c).createUnmarshaller().unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Node getNode(String name, NodeList nodeList) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node itemAttr = nodeList.item(i);
			if (name.equals(itemAttr.getNodeName())) {
    			return itemAttr;
    		}
		}
		return null;
	}	
	
	private String getNodeText(String name, NodeList nodeList) {
		Node itemAttr = getNode(name, nodeList);
		return itemAttr == null ? "" : itemAttr.getTextContent();		
	}
	
	private String getAttrValue(String name, String attr, NodeList nodeList) {
		Node node = getNode(name, nodeList);
		if (node != null) {
			NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				Node itemAttr = attrs.item(i);
				if (attr.equals(itemAttr.getNodeName())) {
	    			return itemAttr.getNodeValue();
	    		}
			}
		}		
		return "";
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public static void main(String[] args) throws WowClientException {
		WowHeadClient client = new WowHeadClient();
		System.out.println(client.getItem2(123915));
//		System.out.println(client.getItem(124106));
//		System.out.println(client.getItem(14970));
//		System.out.println(client.getItem(114821));
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author MinhDuc
 */
public class XMLUtils implements Serializable{
    
    public static String convertHTMLToString(String sUrl) throws MalformedURLException, IOException {        
        URL pageURL = new URL(sUrl);
        InputStream is = pageURL.openStream();
        InputStreamReader reader = new InputStreamReader(is, "UTF-8"); // XIN ĐỪNG QUÊN!!!
        BufferedReader br = new BufferedReader(reader);
        String result = "";
        String line;
        while((line = br.readLine())!=null) {
            result += line;
        }
        return result;
    }
        
    public static Document parseDOMFromFile(String xmlFilePath) 
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        System.out.println("xml path:"+xmlFilePath);
        Document doc = builder.parse(xmlFilePath);
        
        return doc;
    }
    
    public static XPath createXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        return xpath;
    }
    
    public static void transformDOMtoFile(Node node, String xmlFilePath) 
            throws TransformerConfigurationException, TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer =  factory.newTransformer();
        
        Source src = new DOMSource();
        Result res = new StreamResult();
        
        transformer.transform(src, res);
    }
    
    public static Element createElement(String name, String content, Map<String, String> attrs, Document doc) {
        if (name==null) {
            return null;
        }
        if (name.isEmpty()) {
            return null;
        }
        Element result = doc.createElement(name);
        if(content!=null) {
            result.setTextContent(content);
        }
        if (attrs !=null) {
            if(attrs.size()>0){
                for(Map.Entry<String, String> attr: attrs.entrySet()) {
                    result.setAttribute(attr.getKey(), attr.getValue());
                }
            }
        }
        return result;
    }
    
    public static void parseSAXFromFile(String xmlFilePath, DefaultHandler handler) throws SAXException, IOException {
        //SAXParserFactory spf = SAXParserFactory.newInstance();
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(handler);
        parser.parse(xmlFilePath);
    }
    
    public static XMLStreamReader parseFileToStAXCursor(InputStream is) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader reader = factory.createXMLStreamReader(is);
        return reader;
    }
    
    public static String getTextContent(String name, XMLStreamReader reader) 
            throws XMLStreamException {
        if (name==null) {
            return "";
        }
        if (reader==null) {
            return "";
        }
        if (name.isEmpty()) {
            return "";
        }
        
        while(reader.hasNext()) {
            int currentCursor = reader.getEventType();
            if (currentCursor == XMLStreamConstants.START_ELEMENT) {
                String tagName = reader.getLocalName();
                if (tagName.equals(name)) {
                    reader.next(); //value
                    String result = reader.getText();
                    reader.nextTag();
                    return result;
                }
            }
            reader.next();
        }
        
        return "";
    }
    
}

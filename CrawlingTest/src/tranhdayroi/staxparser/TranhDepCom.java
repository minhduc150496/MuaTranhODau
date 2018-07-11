/*
 * To change this license header, choose License Headers in Project Propertiehtml.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.staxparser;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import tranhdayroi.jaxb.Painting;
import tranhdayroi.utils.HTMLPreprocessor;
import tranhdayroi.domparser.TuongXinhComVn;
import tranhdayroi.utils.StringUtils;
import tranhdayroi.domparser.Parser;

/**
 *
 * @author MinhDuc
 */
public class TranhDepCom implements Parser {

    private final String domainCode = "TD"; // TD: TranhDep
    private List<Painting> results;

    @Override
    public List<Painting> getResults() {
        return results;
    }

    @Override
    public void parse() {
        results = null;
        String[] pageList = {
            "http://www.tranh-dep.com/tranh-tranh-tri-noi-that.html",
            "http://www.tranh-dep.com/tranh-trang-tri-phong-ngu",
            "http://www.tranh-dep.com/tranh-trang-tri-phong-khach-dep",
            "http://www.tranh-dep.com/tranh-trang-tri-phong-an",
            "http://www.tranh-dep.com/tranh-tang-tan-gia",
            "http://www.tranh-dep.com/tranh-dong-ho-hoa-qua.html",
            "http://www.tranh-dep.com/tranh-dong-ho-phong-canh.html",
            "http://www.tranh-dep.com/tranh-dong-ho-dong-vat-con-trung.html",
            "http://www.tranh-dep.com/tranh-dong-ho-hoat-hinh.html",
            "http://www.tranh-dep.com/tranh-dong-ho-trang-tri.html",
            "http://www.tranh-dep.com/tranh-dong-ho-de-ban.html",
            "http://www.tranh-dep.com/tranh-hoa-si-noi-tieng.html",
            "http://www.tranh-dep.com/tranh-hoa-treo-tuong",
            "http://www.tranh-dep.com/tranh-phong-canh-treo-tuong",
            "http://www.tranh-dep.com/tranh-treo-tuong-phong-thuy",
            "http://www.tranh-dep.com/tranh-treo-tuong-vintage",
            "http://www.tranh-dep.com/tranh-bo-hien-dai"
        };

        System.out.println("get HTML...");
        List<String> htmlList = new ArrayList<>();
        for (int i = 0; i < pageList.length; i++) {
            String pageURL = pageList[i];
            try {
                String html = "";
                html = HTMLPreprocessor.getHTMLFromURL(pageURL);

                htmlList.add(html);

                System.out.printf("get: %d/%d\n", i, pageList.length);
            } catch (IOException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("done.");

        System.out.println("Preprocessing...");
        List<String> xmlList = new ArrayList<>();
        for (int i = 0; i < htmlList.size(); i++) {
            try {
                String html = htmlList.get(i);

                int start = html.indexOf("<div id=\"bd_results\">");
                int end = html.indexOf("<div class=\"ot-bottom-content\">");
                html = html.substring(start, end);

                String xml = HTMLPreprocessor.wellForm(html);
                xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xml;
                xmlList.add(xml);

                System.out.printf("get: %d/%d\n", i, pageList.length);
            } catch (StringIndexOutOfBoundsException ex) {
                Logger.getLogger(TuongXinhComVn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("done.");

        for (int i = 0; i < xmlList.size(); i++) {
            try {
                String xml = xmlList.get(i);
                parse(xml);
                System.out.println("Parsed: " + (i) + "/" + pageList.length);
            } catch (IOException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (XMLStreamException ex) {
                Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void parse(String xml) throws
            IOException, ParserConfigurationException, SAXException,
            XPathExpressionException, XMLStreamException {

        XMLInputFactory factory = XMLInputFactory.newFactory();
        StringReader sr = new StringReader(xml);
        XMLStreamReader reader = factory.createXMLStreamReader(sr);

        boolean inProductNameDiv = false;
        boolean inTagContainingName = false;
        boolean inTagContainingPrice = false;
        boolean inTagContainingCode = false;
        Painting newPainting = new Painting();

        while (reader.hasNext()) {
            int eventType = reader.getEventType();
            switch (eventType) {
                case XMLStreamConstants.START_ELEMENT:
                    String tagName = reader.getLocalName();
                    String sClass = reader.getAttributeValue(null, "class");
                    if (sClass == null) {
                        sClass = "";
                    }

                    if (tagName.equals("img")) {
                        // get imageURL
                        String imageURL = reader.getAttributeValue(null, "src");
                        newPainting.setImageURL(imageURL);
                    } else if (inProductNameDiv && tagName.equals("a")) {
                        inTagContainingName = true;
                    } else if (sClass.contains("product-addtocart")) {
                        // complete reading a new painting -> add to List
                        if (results == null) {
                            results = new ArrayList<>();
                        }
                        String keywords = StringUtils.toRawString(newPainting.getName());
                        newPainting.setKeywords(keywords);
                        results.add(newPainting);
                        newPainting = new Painting();
                    } else if (sClass.contains("product-name")) {
                        // found div that has class="product-name"
                        inProductNameDiv = true;
                    } else if (tagName.equals("span") && sClass.contains("PricesalesPrice")) {
                        // found price
                        inTagContainingPrice = true;
                    } else if (sClass.contains("product-sku")) {
                        // found code
                        inTagContainingCode = true;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (inTagContainingName) {
                        // get name
                        String name = reader.getText().trim();
                        newPainting.setName(name);
                    } else if (inTagContainingPrice) {
                        // get price
                        String sPrice = reader.getText().trim();
                        sPrice = sPrice.replace(".", "");
                        sPrice = sPrice.replace("đ", "");
                        sPrice = sPrice.trim();
                        BigInteger price = new BigInteger("-1");
                        try {
                            price = new BigInteger(sPrice);
                        } catch (NumberFormatException ex) {
                            Logger.getLogger(TranhDepCom.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        newPainting.setPrice(price);
                    } else if (inTagContainingCode) {
                        // get code
                        String code = reader.getText();
                        newPainting.setCode(code);
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    tagName = reader.getLocalName();
                    if (inProductNameDiv && tagName.equals("div")) {
                        inProductNameDiv = false; // out div
                    } else if (inTagContainingName && tagName.equals("a")) {
                        inTagContainingName = false;
                    } else if (inTagContainingCode && tagName.equals("div")) {
                        inTagContainingCode = false;
                    } else if (inTagContainingPrice && tagName.equals("span")) {
                        inTagContainingPrice = false;
                    }
                    break;
            }// end switch
            reader.next();
        }// end while

    }

}

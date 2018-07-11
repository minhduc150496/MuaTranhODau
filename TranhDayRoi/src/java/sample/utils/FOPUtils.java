/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.xalan.transformer.TransformerImpl;
import org.xml.sax.SAXException;
import org.apache.xmlgraphics.image.loader.*;

/**
 *
 * @author MinhDuc
 */
public class FOPUtils implements Serializable {

    public static String convertXmlAndXslToFO(String xml, String pathXSL) 
            throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        TransformerFactory tf = TransformerFactory.newInstance();
        StreamSource ssXSL = new StreamSource(pathXSL);
        Transformer trans = tf.newTransformer(ssXSL);

        StreamSource ssXML = new StreamSource(new StringReader(xml));
        StringWriter sw = new StringWriter();
        StreamResult srFO = new StreamResult(sw);
        trans.transform(ssXML, srFO);

        return sw.toString();
    }

    public static String convertXmlAndXslToFO(InputStream insXML, String pathXSL) 
            throws TransformerConfigurationException, TransformerException, FileNotFoundException {
        TransformerFactory tf = TransformerFactory.newInstance();
        StreamSource ssXSL = new StreamSource(pathXSL);
        Transformer trans = tf.newTransformer(ssXSL);

        StreamSource ssXML = new StreamSource(insXML);
        StringWriter sw = new StringWriter();
        StreamResult srFO = new StreamResult(sw);
        trans.transform(ssXML, srFO);

        return sw.toString();
    }

    public static void convertFOToPDF(String pathFopConf, String sFO, OutputStream ousPDF)
            throws SAXException, IOException, TransformerConfigurationException, TransformerException {
        File fopConfig = new File(pathFopConf);
        FopFactory ff = FopFactory.newInstance(fopConfig);
        FOUserAgent fua = ff.newFOUserAgent();

        Fop fop = ff.newFop(MimeConstants.MIME_PDF, fua, ousPDF);

        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer trans = tff.newTransformer();

        Result result = new SAXResult(fop.getDefaultHandler());

        StreamSource source = new StreamSource(new StringReader(sFO));
        trans.transform(source, result);
    }

    public static void convertFOToPDF(String pathFopConf, InputStream insFO, OutputStream ousPDF)
            throws SAXException, IOException, TransformerConfigurationException, TransformerException {
        File fopConfig = new File(pathFopConf);
        FopFactory ff = FopFactory.newInstance(fopConfig);
        FOUserAgent fua = ff.newFOUserAgent();

        Fop fop = ff.newFop(MimeConstants.MIME_PDF, fua, ousPDF);

        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer trans = tff.newTransformer();

        Result result = new SAXResult(fop.getDefaultHandler());

        StreamSource source = new StreamSource(insFO);
        trans.transform(source, result);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.utils;

import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

/**
 *
 * @author MinhDuc
 */
public class JAXBUtils implements Serializable {

    public static String marshallObject(Object obj, Class c) throws PropertyException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(c);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter sw = new StringWriter();
        m.marshal(obj, sw);
        String result = sw.toString();
        return result;
    }
        
    public static void marshallObject(Object obj, Class c, OutputStream ous) throws PropertyException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(c);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        m.marshal(obj, ous);
    }
}

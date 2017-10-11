/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip.params;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vasiliy.Andricov
 */
@XmlRootElement(name = "response")
public class result {

    private String resultCode;
    private String resultComment;
    private String lastCommand;

    @XmlAttribute
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @XmlAttribute
    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
    }

    @XmlAttribute
    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public String convertObjectToXml() {
        try {
            JAXBContext context = JAXBContext.newInstance(getClass());
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в строку
            StringWriter sw = new StringWriter();
            marshaller.marshal(this, sw);
            return sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public result convertFromXml(String xml) {
        result res = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(getClass());

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader sw = new StringReader(xml);
            //sw.write(xml);
            res = (result) jaxbUnmarshaller.unmarshal(sw);
            System.out.println(res);
        } catch (Exception e) {
        }
        return res;
    }

    @Override
    public String toString() {
        return "result{\n\t" + "resultCode=" + resultCode + ",\n\t resultComment=" + resultComment + ",\n\t lastCommand=" + lastCommand + '}';
    }

}

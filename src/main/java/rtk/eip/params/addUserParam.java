/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip.params;

import java.io.StringWriter;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasil
 */
@XmlRootElement(name="request")
public class addUserParam {
    private String user;
    private int autoCreateFlag;
    private String surname;
    private String name;
    private String patronymic;
    private Date dob;
    private String gender;
    private String region;
    private String contactEmail;
    private String contactPhone;
    private String password;

    public addUserParam() {
    }

    @XmlAttribute
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    @XmlAttribute
    public int getAutoCreateFlag() {
        return autoCreateFlag;
    }

    public void setAutoCreateFlag(int autoCreateFlag) {
        this.autoCreateFlag = autoCreateFlag;
    }
    @XmlAttribute
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    @XmlAttribute
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    @XmlAttribute
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    @XmlAttribute
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    @XmlAttribute
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    @XmlAttribute
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    @XmlAttribute
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "addUserParam{" + "user=" + user + ", autoCreateFlag=" + autoCreateFlag + ", surname=" + surname + ", name=" + name + ", patronymic=" + patronymic + ", dob=" + dob + ", gender=" + gender + ", region=" + region + ", contactEmail=" + contactEmail + ", contactPhone=" + contactPhone + ", password=" + password + '}';
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
}

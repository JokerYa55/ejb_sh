/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip.params;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasil
 */
@XmlRootElement(name = "request")
public class changePasswordParam {

    private String user;
    private String hash;
    private String hashType;
    private String salt;
    private String reqType;

    public changePasswordParam() {
    }

    @XmlAttribute
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @XmlAttribute
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @XmlAttribute
    public String getHashType() {
        return hashType;
    }

    public void setHashType(String hashType) {
        this.hashType = hashType;
    }

    @XmlAttribute
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @XmlAttribute
    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    @Override
    public String toString() {
        return "changePasswordParam{" + "user=" + user + ", hash=" + hash + ", hashType=" + hashType + ", salt=" + salt + ", reqType=" + reqType + '}';
    }

}

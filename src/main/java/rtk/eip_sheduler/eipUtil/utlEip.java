/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.eipUtil;

import java.net.URL;
import rtk.eip_sheduler.beans.UserEntity;
import org.apache.log4j.Logger;
import rtk.eip.params.addUserParam;

import rtk.eip.params.changePasswordParam;
import rtk.eip.params.updUserParam;
import rtk.eip_sheduler.XMLUtil.utlXML;
//import static rtk.eip_sheduler.XMLUtil.utlXML.xmlToString;
import rtk.eip_sheduler.httpUtil.utlHttp;

/**
 *
 * @author vasil
 */
public class utlEip {

    private final URL url;
    private final Logger log = Logger.getLogger(getClass().getName());

    public utlEip(URL url) {
        this.url = url;
    }

    /**
     *
     * @param user
     * @return
     */
    public String addUser(UserEntity user) {
        log.debug("ADD_USER");
        String res = null;
        try {
            addUserParam param = new addUserParam();
            param.setContactEmail(user.getEmail());
            param.setContactPhone(user.getPhone());
            param.setReqType("CREATE_USER_PASSWORD");
            param.setSalt(user.getSalt());
            param.setHash(user.getPassword());
            param.setHash_type(user.getHesh_type().toUpperCase());
            param.setUser(user.getUsername());
            param.setSurname(user.getFirstName());
            param.setName(user.getLastName());
            param.setPatronymic(user.getThirdName());
            if (user.getUser_region() != null) {
                param.setRegion(user.getUser_region().toString());
            }

            utlHttp http = new utlHttp();
            utlXML utlxml = new utlXML();

            String dataXml = utlxml.convertObjectToXml(param);
            log.debug("dataXml => " + dataXml);
            res = http.doPost(url.toString(), dataXml, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     *
     * @param user
     * @return
     */
    public String updateUser(UserEntity user) {
        log.debug("UPD_USER");
        String res = null;

        try {
            updUserParam param = new updUserParam();
            if (user.getEmail() != null) {
                param.setContactEmail(user.getEmail());
            }
            if (user.getPhone() != null) {
                param.setContactPhone(user.getPhone());
            }

            param.setReqType("EDIT_USER");
            if (user.getUsername() != null) {
                param.setUser(user.getUsername());
            }
            if (user.getFirstName() != null) {
                param.setSurname(user.getFirstName());
            }
            if (user.getLastName() != null) {
                param.setName(user.getLastName());
            }
            if (user.getThirdName() != null) {
                param.setPatronymic(user.getThirdName());
            }
            if (user.getUser_region() != null) {
                param.setRegion(user.getUser_region().toString());
            }
            if ((user.getUser_status() != null) && (user.getUser_status() != 0)) {
                param.setUserStatus(user.getUser_status().toString());
            }

            utlHttp http = new utlHttp();
            utlXML utlxml = new utlXML();
            String dataXml = utlxml.convertObjectToXml(param);
            log.debug("dataXml => " + dataXml);
            res = http.doPost(url.toString(), dataXml, null);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     *
     * @param user
     * @return
     */
    public String changePassword(UserEntity user) {
        log.debug("CHANGE_PASSWORD => " + user);
        String res = null;
        try {
            changePasswordParam param = new changePasswordParam();
            param.setHash(user.getPassword());
            param.setHashType(user.getHesh_type().toUpperCase());
            param.setReqType("CHANGE_PASSWORD");
            param.setSalt(user.getSalt());
            param.setUser(user.getUsername());

            utlHttp http = new utlHttp();
            utlXML utlxml = new utlXML();
            String dataXml = utlxml.convertObjectToXml(param);
            log.debug("dataXml => " + dataXml);
            res = http.doPost(url.toString(), dataXml, null);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

}

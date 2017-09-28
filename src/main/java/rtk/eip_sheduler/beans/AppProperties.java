/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.beans;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author vasiliy.andricov
 */
@NamedQueries({
    @NamedQuery(name = "findAll", query = "SELECT t FROM AppProperties t")
    , 
    @NamedQuery(name = "findByProperties", query = "SELECT t FROM AppProperties t WHERE t.param_name=:pname")})

@Entity
@Table(name = "t_app_properties")
//, indexes = {
//@Index(name = "t_app_properties_pname_idx", columnList = "param_name")}
public class AppProperties implements Serializable {

    @Id
    @Column(name = "param_name", unique = true, nullable = false)
    private String param_name;
    @Column(name = "param_value", unique = false, nullable = false)
    private String param_value;

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }

    public String getParam_value() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }

    @Override
    public String toString() {
        return "AppProperties{" + "param_name=" + param_name + ", param_value=" + param_value + '}';
    }
}

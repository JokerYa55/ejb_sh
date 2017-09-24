/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "t_user_attribute")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAttribute.findAll", query = "SELECT t FROM UserAttribute t")
    , @NamedQuery(name = "UserAttribute.findById", query = "SELECT t FROM UserAttribute t WHERE t.id = :id")
    , @NamedQuery(name = "UserAttribute.findByName", query = "SELECT t FROM UserAttribute t WHERE t.name = :name")
    , @NamedQuery(name = "UserAttribute.findByValue", query = "SELECT t FROM UserAttribute t WHERE t.value = :value")
    , @NamedQuery(name = "UserAttribute.findByVisibleFlag", query = "SELECT t FROM UserAttribute t WHERE t.visibleFlag = :visibleFlag")})
public class UserAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @Column(name = "visible_flag")
    private boolean visibleFlag;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserEntity userId;

    public UserAttribute() {
    }

    public UserAttribute(Long id) {
        this.id = id;
    }

    public UserAttribute(Long id, String name, String value, boolean visibleFlag) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.visibleFlag = visibleFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getVisibleFlag() {
        return visibleFlag;
    }

    public void setVisibleFlag(boolean visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAttribute)) {
            return false;
        }
        UserAttribute other = (UserAttribute) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rtk.eip_sheduler.beans.TUserAttribute[ id=" + id + " ]";
    }
    
}

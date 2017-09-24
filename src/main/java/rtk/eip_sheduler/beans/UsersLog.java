/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vasil
 */
@Entity
@Table(name = "t_users_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersLog.findAll", query = "SELECT t FROM UsersLog t")
    , @NamedQuery(name = "UsersLog.findById", query = "SELECT t FROM UsersLog t WHERE t.id = :id")
    , @NamedQuery(name = "UsersLog.findByDateOper", query = "SELECT t FROM UsersLog t WHERE t.dateOper = :dateOper")
    , @NamedQuery(name = "UsersLog.findByFlag", query = "SELECT t FROM UsersLog t WHERE t.flag = :flag")
    , @NamedQuery(name = "UsersLog.findByInfo", query = "SELECT t FROM UsersLog t WHERE t.info = :info")
    , @NamedQuery(name = "UsersLog.findByLastCommand", query = "SELECT t FROM UsersLog t WHERE t.lastCommand = :lastCommand")
    , @NamedQuery(name = "UsersLog.findByOperType", query = "SELECT t FROM UsersLog t WHERE t.operType = :operType")
    , @NamedQuery(name = "UsersLog.findBySendCount", query = "SELECT t FROM UsersLog t WHERE t.sendCount = :sendCount")
    , @NamedQuery(name = "UsersLog.findByUserId", query = "SELECT t FROM UsersLog t WHERE t.userId = :userId")
    , @NamedQuery(name = "UsersLog.findByUsername", query = "SELECT t FROM UsersLog t WHERE t.username = :username")})
public class UsersLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "date_oper")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOper;
    @Basic(optional = false)
    @Column(name = "flag")
    private boolean flag;
    @Column(name = "info")
    private String info;
    @Column(name = "last_command")
    private String lastCommand;
    @Column(name = "oper_type")
    private String operType;
    @Basic(optional = false)
    @Column(name = "send_count")
    private int sendCount;
    @Basic(optional = false)
    @Column(name = "user_id")
    private long userId;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    public UsersLog() {
    }

    public UsersLog(Long id) {
        this.id = id;
    }

    public UsersLog(Long id, Date dateOper, boolean flag, int sendCount, long userId, String username) {
        this.id = id;
        this.dateOper = dateOper;
        this.flag = flag;
        this.sendCount = sendCount;
        this.userId = userId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateOper() {
        return dateOper;
    }

    public void setDateOper(Date dateOper) {
        this.dateOper = dateOper;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        if (!(object instanceof UsersLog)) {
            return false;
        }
        UsersLog other = (UsersLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TUsersLog{" + "id=" + id + ", dateOper=" + dateOper + ", flag=" + flag + ", info=" + info + ", lastCommand=" + lastCommand + ", operType=" + operType + ", sendCount=" + sendCount + ", userId=" + userId + ", username=" + username + '}';
    }

   
    
}

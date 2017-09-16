/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.eip_sheduler.beans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.apache.log4j.Logger;

/**
 *
 * @author vasil
 */
@NamedQueries({
    @NamedQuery(name = "TUsers.findAll", query = "SELECT t FROM TUsers t where t.user_status = 0")
    , @NamedQuery(name = "TUsers.findById", query = "SELECT t FROM TUsers t WHERE t.id = :id")
    //, @NamedQuery(name = "TUsers.findByAddress", query = "SELECT t FROM TUsers t WHERE t.address = :address and t.user_status = 0")
    , @NamedQuery(name = "TUsers.findByEmail", query = "SELECT t FROM TUsers t WHERE t.email = :email and t.user_status = 0")
    , @NamedQuery(name = "TUsers.findByHashType", query = "SELECT t FROM TUsers t WHERE t.hesh_type = :hashType and t.user_status = 0")
    , @NamedQuery(name = "TUsers.findByPassword", query = "SELECT t FROM TUsers t WHERE t.password = :password and t.user_status = 0")
    , @NamedQuery(name = "TUsers.findByPasswordNotHash", query = "SELECT t FROM TUsers t WHERE t.password_not_hash = :passwordNotHash and t.user_status = 0")
    , @NamedQuery(name = "TUsers.findByPhone", query = "SELECT t FROM TUsers t WHERE t.phone = :phone and t.user_status = 0")
    , @NamedQuery(name = "TUsers.findByUsername", query = "SELECT t FROM TUsers t WHERE t.username = :username and t.user_status = 0")
    , @NamedQuery(name = "TUsers.findBySalt", query = "SELECT t FROM TUsers t WHERE t.salt = :salt and t.user_status = 0")})
@Entity
@Table(name = "t_users", indexes = {
    @Index(name = "t_users_status_idx", columnList = "user_status")
    ,
    @Index(name = "t_users_username_idx", columnList = "username")})
public class TUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(TUsers.class);

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_users_id_seq")
    @SequenceGenerator(name = "t_users_id_seq", sequenceName = "t_users_id_seq", allocationSize = 1)
    private Long id;
    // Логин
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    // Фамилия    
    @Column(name = "firstName", unique = false, nullable = true)
    private String first_name;
    // Имя
    @Column(name = "lastName", unique = false, nullable = true)
    private String second_name;
    // Отчество
    @Column(name = "thirdName", unique = false, nullable = true)
    private String third_name;
    // e-mail
    @Column(name = "email", unique = true, nullable = true)
    private String email;
    // Пароль
    @Column(name = "password", nullable = true)
    private String password;
    // Незашифрованный пароль
    @Column(name = "password_not_hash", nullable = true)
    private String password_not_hash;
    // Телефон
    @Column(name = "phone", nullable = true)
    private String phone;
    // Адрес
    //@Column(name = "address", nullable = true)
    //private String address;
    @Column(name = "hash_type", nullable = true)
    private String hesh_type;
    @Column(name = "salt", nullable = true)
    private String salt;
    @Column(name = "id_app_1", unique = true, nullable = true)
    private String id_app_1;
    @Column(name = "id_app_2", unique = true, nullable = true)
    private String id_app_2;
    @Column(name = "id_app_3", unique = true, nullable = true)
    private String id_app_3;
    @Column(name = "id_app_4", unique = true, nullable = true)
    private String id_app_4;
    @Column(name = "id_app_5", unique = true, nullable = true)
    private String id_app_5;
    @Column(name = "id_app_6", unique = true, nullable = true)
    private String id_app_6;
    @Column(name = "id_app_7", unique = true, nullable = true)
    private String id_app_7;
    @Column(name = "id_app_8", unique = true, nullable = true)
    private String id_app_8;
    @Column(name = "id_app_9", unique = true, nullable = true)
    private String id_app_9;
    @Column(name = "id_app_10", unique = true, nullable = true)
    private String id_app_10;
    @Column(name = "id_app_11", unique = true, nullable = true)
    private String id_app_11;
    @Column(name = "id_app_12", unique = true, nullable = true)
    private String id_app_12;
    @Column(name = "id_app_13", unique = true, nullable = true)
    private String id_app_13;
    @Column(name = "id_app_14", unique = true, nullable = true)
    private String id_app_14;
    @Column(name = "id_app_15", unique = true, nullable = true)
    private String id_app_15;
    @Column(name = "id_app_16", unique = true, nullable = true)
    private String id_app_16;
    @Column(name = "id_app_17", unique = true, nullable = true)
    private String id_app_17;
    @Column(name = "id_app_18", unique = true, nullable = true)
    private String id_app_18;
    @Column(name = "id_app_19", unique = true, nullable = true)
    private String id_app_19;
    @Column(name = "id_app_20", unique = true, nullable = true)
    private String id_app_20;
    @Column(name = "id_app_21", unique = true, nullable = true)
    private String id_app_21;
    @Column(name = "id_app_22", unique = true, nullable = true)
    private String id_app_22;
    @Column(name = "id_app_23", unique = true, nullable = true)
    private String id_app_23;
    @Column(name = "id_app_24", unique = true, nullable = true)
    private String id_app_24;
    @Column(name = "id_app_25", unique = true, nullable = true)
    private String id_app_25;
    @Column(name = "id_app_27", unique = true, nullable = true)
    private String id_app_27;
    @Column(name = "id_app_28", unique = true, nullable = true)
    private String id_app_28;
    @Column(name = "id_app_29", unique = true, nullable = true)
    private String id_app_29;
    @Column(name = "id_app_30", unique = true, nullable = true)
    private String id_app_30;
    @Column(name = "user_status", unique = false, nullable = false, columnDefinition = "integer DEFAULT 0")
    private Integer user_status;
    @Column(name = "create_date", unique = false, nullable = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date create_date;
//    @Column(name = "date_birthday", unique = false, nullable = true)
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date date_birthday;
//    @Column(name = "user_gender", unique = false, nullable = true)
//    private Integer user_gender;
    @Column(name = "user_region", unique = false, nullable = true)
    private Integer user_region;
    @Column(name = "enabled", unique = false, nullable = false, columnDefinition = "boolean DEFAULT true")
    private boolean enabled;
    @Column(name = "description", unique = false, nullable = true)
    private String description;

    public TUsers() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getThird_name() {
        return third_name;
    }

    public void setThird_name(String third_name) {
        this.third_name = third_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_not_hash() {
        return password_not_hash;
    }

    public void setPassword_not_hash(String password_not_hash) {
        this.password_not_hash = password_not_hash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHesh_type() {
        return hesh_type;
    }

    public void setHesh_type(String hesh_type) {
        this.hesh_type = hesh_type;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getId_app_1() {
        return id_app_1;
    }

    public void setId_app_1(String id_app_1) {
        this.id_app_1 = id_app_1;
    }

    public String getId_app_2() {
        return id_app_2;
    }

    public void setId_app_2(String id_app_2) {
        this.id_app_2 = id_app_2;
    }

    public String getId_app_3() {
        return id_app_3;
    }

    public void setId_app_3(String id_app_3) {
        this.id_app_3 = id_app_3;
    }

    public String getId_app_4() {
        return id_app_4;
    }

    public void setId_app_4(String id_app_4) {
        this.id_app_4 = id_app_4;
    }

    public String getId_app_5() {
        return id_app_5;
    }

    public void setId_app_5(String id_app_5) {
        this.id_app_5 = id_app_5;
    }

    public String getId_app_6() {
        return id_app_6;
    }

    public void setId_app_6(String id_app_6) {
        this.id_app_6 = id_app_6;
    }

    public String getId_app_7() {
        return id_app_7;
    }

    public void setId_app_7(String id_app_7) {
        this.id_app_7 = id_app_7;
    }

    public String getId_app_8() {
        return id_app_8;
    }

    public void setId_app_8(String id_app_8) {
        this.id_app_8 = id_app_8;
    }

    public String getId_app_9() {
        return id_app_9;
    }

    public void setId_app_9(String id_app_9) {
        this.id_app_9 = id_app_9;
    }

    public String getId_app_10() {
        return id_app_10;
    }

    public void setId_app_10(String id_app_10) {
        this.id_app_10 = id_app_10;
    }

    public String getId_app_11() {
        return id_app_11;
    }

    public void setId_app_11(String id_app_11) {
        this.id_app_11 = id_app_11;
    }

    public String getId_app_12() {
        return id_app_12;
    }

    public void setId_app_12(String id_app_12) {
        this.id_app_12 = id_app_12;
    }

    public String getId_app_13() {
        return id_app_13;
    }

    public void setId_app_13(String id_app_13) {
        this.id_app_13 = id_app_13;
    }

    public String getId_app_14() {
        return id_app_14;
    }

    public void setId_app_14(String id_app_14) {
        this.id_app_14 = id_app_14;
    }

    public String getId_app_15() {
        return id_app_15;
    }

    public void setId_app_15(String id_app_15) {
        this.id_app_15 = id_app_15;
    }

    public String getId_app_16() {
        return id_app_16;
    }

    public void setId_app_16(String id_app_16) {
        this.id_app_16 = id_app_16;
    }

    public String getId_app_17() {
        return id_app_17;
    }

    public void setId_app_17(String id_app_17) {
        this.id_app_17 = id_app_17;
    }

    public String getId_app_18() {
        return id_app_18;
    }

    public void setId_app_18(String id_app_18) {
        this.id_app_18 = id_app_18;
    }

    public String getId_app_19() {
        return id_app_19;
    }

    public void setId_app_19(String id_app_19) {
        this.id_app_19 = id_app_19;
    }

    public String getId_app_20() {
        return id_app_20;
    }

    public void setId_app_20(String id_app_20) {
        this.id_app_20 = id_app_20;
    }

    public String getId_app_21() {
        return id_app_21;
    }

    public void setId_app_21(String id_app_21) {
        this.id_app_21 = id_app_21;
    }

    public String getId_app_22() {
        return id_app_22;
    }

    public void setId_app_22(String id_app_22) {
        this.id_app_22 = id_app_22;
    }

    public String getId_app_23() {
        return id_app_23;
    }

    public void setId_app_23(String id_app_23) {
        this.id_app_23 = id_app_23;
    }

    public String getId_app_24() {
        return id_app_24;
    }

    public void setId_app_24(String id_app_24) {
        this.id_app_24 = id_app_24;
    }

    public String getId_app_25() {
        return id_app_25;
    }

    public void setId_app_25(String id_app_25) {
        this.id_app_25 = id_app_25;
    }

    public String getId_app_27() {
        return id_app_27;
    }

    public void setId_app_27(String id_app_27) {
        this.id_app_27 = id_app_27;
    }

    public String getId_app_28() {
        return id_app_28;
    }

    public void setId_app_28(String id_app_28) {
        this.id_app_28 = id_app_28;
    }

    public String getId_app_29() {
        return id_app_29;
    }

    public void setId_app_29(String id_app_29) {
        this.id_app_29 = id_app_29;
    }

    public String getId_app_30() {
        return id_app_30;
    }

    public void setId_app_30(String id_app_30) {
        this.id_app_30 = id_app_30;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Integer getUser_region() {
        return user_region;
    }

    public void setUser_region(Integer user_region) {
        this.user_region = user_region;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TUsers{" + "id=" + id + ", username=" + username + ", first_name=" + first_name + ", second_name=" + second_name + ", third_name=" + third_name + ", email=" + email + ", password=" + password + ", password_not_hash=" + password_not_hash + ", phone=" + phone + ", hesh_type=" + hesh_type + ", salt=" + salt + ", id_app_1=" + id_app_1 + ", id_app_2=" + id_app_2 + ", id_app_3=" + id_app_3 + ", id_app_4=" + id_app_4 + ", id_app_5=" + id_app_5 + ", id_app_6=" + id_app_6 + ", id_app_7=" + id_app_7 + ", id_app_8=" + id_app_8 + ", id_app_9=" + id_app_9 + ", id_app_10=" + id_app_10 + ", id_app_11=" + id_app_11 + ", id_app_12=" + id_app_12 + ", id_app_13=" + id_app_13 + ", id_app_14=" + id_app_14 + ", id_app_15=" + id_app_15 + ", id_app_16=" + id_app_16 + ", id_app_17=" + id_app_17 + ", id_app_18=" + id_app_18 + ", id_app_19=" + id_app_19 + ", id_app_20=" + id_app_20 + ", id_app_21=" + id_app_21 + ", id_app_22=" + id_app_22 + ", id_app_23=" + id_app_23 + ", id_app_24=" + id_app_24 + ", id_app_25=" + id_app_25 + ", id_app_27=" + id_app_27 + ", id_app_28=" + id_app_28 + ", id_app_29=" + id_app_29 + ", id_app_30=" + id_app_30 + ", user_status=" + user_status + ", create_date=" + create_date + ", user_region=" + user_region + ", enabled=" + enabled + ", description=" + description + '}';
    }

}

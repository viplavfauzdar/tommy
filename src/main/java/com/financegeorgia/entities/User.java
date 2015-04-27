/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.financegeorgia.entities;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonDateFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Viplav
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findAllByFirstName", query = "SELECT u FROM User u WHERE u.firstName like :firstName"),
    @NamedQuery(name = "User.search", query = "SELECT u FROM User u WHERE (u.firstName like :keyword or u.lastName like :keyword or u.email like :keyword)"),
    @NamedQuery(name = "User.findByMi", query = "SELECT u FROM User u WHERE u.mi = :mi"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByPassword1", query = "SELECT u FROM User u WHERE u.password1 = :password1"),
    @NamedQuery(name = "User.findByUserType", query = "SELECT u FROM User u WHERE u.userType = :userType"),
    @NamedQuery(name = "User.findByPptosnda", query = "SELECT u FROM User u WHERE u.pptosnda = :pptosnda"),
    @NamedQuery(name = "User.findByDob", query = "SELECT u FROM User u WHERE u.dob = :dob"),
    @NamedQuery(name = "User.findByAboutMe", query = "SELECT u FROM User u WHERE u.aboutMe = :aboutMe"),
    @NamedQuery(name = "User.findByLocked", query = "SELECT u FROM User u WHERE u.locked = :locked"),
    @NamedQuery(name = "User.findByPassresetKey", query = "SELECT u FROM User u WHERE u.passresetKey = :passresetKey")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "mi")
    private String mi;
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "password1")
    private String password1;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "pptosnda")
    private Boolean pptosnda;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "locked")
    private Short locked;
    @Column(name = "passreset_key")
    private String passresetKey;
    
    //for google recaptcha
    //transient makes it not persistable
//    @Transient
//    private String recaptcha;
//    public void setRecaptcha(String recaptcha){
//        this.recaptcha = recaptcha;
//    }
//    public String getRecaptcha(){
//        return recaptcha;
//    }
    

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String email, String password1) {
        this.id = id;
        this.email = email;
        this.password1 = password1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getPptosnda() {
        return pptosnda;
    }

    public void setPptosnda(Boolean pptosnda) {
        this.pptosnda = pptosnda;
    }

    @JsonDateFormat("MM/dd/yyyy")
    public Date getDob() {
        return dob;
    }

    @JsonDateFormat("MM/dd/yyyy")
    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Short getLocked() {
        return locked;
    }

    public void setLocked(Short locked) {
        this.locked = locked;
    }

    public String getPassresetKey() {
        return passresetKey;
    }

    public void setPassresetKey(String passresetKey) {
        this.passresetKey = passresetKey;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    private final static Genson genson = new Genson();
    @Override
    public String toString() {        
        return "com.financegeorgia.entities.User[ " + genson.serialize(this) + " ]";
    }
    
}

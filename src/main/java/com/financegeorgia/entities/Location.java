/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Cache;
import static org.eclipse.persistence.config.CacheIsolationType.ISOLATED;

/**
 *
 * @author Viplav
 */
@Entity
@Cache(isolation=ISOLATED)
@Table(name = "location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l"),
    @NamedQuery(name = "Location.findById", query = "SELECT l FROM Location l WHERE l.id = :id"),
    @NamedQuery(name = "Location.findByUserEmail", query = "SELECT l FROM Location l INNER JOIN User u WHERE l.userId = u.id and u.email = :email"),
    @NamedQuery(name = "Location.findByUserId", query = "SELECT l FROM Location l WHERE l.userId = :userId"),
    @NamedQuery(name = "Location.findByAddress", query = "SELECT l FROM Location l WHERE l.address = :address"),
    @NamedQuery(name = "Location.findByCity", query = "SELECT l FROM Location l WHERE l.city = :city"),
    @NamedQuery(name = "Location.findByState", query = "SELECT l FROM Location l WHERE l.state = :state"),
    @NamedQuery(name = "Location.findByZip", query = "SELECT l FROM Location l WHERE l.zip = :zip"),
    @NamedQuery(name = "Location.findByCountry", query = "SELECT l FROM Location l WHERE l.country = :country"),
    @NamedQuery(name = "Location.findByPhone1", query = "SELECT l FROM Location l WHERE l.phone1 = :phone1"),
    @NamedQuery(name = "Location.findByPhone2", query = "SELECT l FROM Location l WHERE l.phone2 = :phone2"),
    @NamedQuery(name = "Location.findByMobile", query = "SELECT l FROM Location l WHERE l.mobile = :mobile"),
    @NamedQuery(name = "Location.findByWebsite", query = "SELECT l FROM Location l WHERE l.website = :website"),
    @NamedQuery(name = "Location.findByFacebook", query = "SELECT l FROM Location l WHERE l.facebook = :facebook"),
    @NamedQuery(name = "Location.findByGooglePlus", query = "SELECT l FROM Location l WHERE l.googlePlus = :googlePlus"),
    @NamedQuery(name = "Location.findByTwitter", query = "SELECT l FROM Location l WHERE l.twitter = :twitter"),
    @NamedQuery(name = "Location.findByLinkedin", query = "SELECT l FROM Location l WHERE l.linkedin = :linkedin")})
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private String zip;
    @Column(name = "country")
    private String country;
    @Column(name = "phone1")
    private String phone1;
    @Column(name = "phone2")
    private String phone2;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "website")
    private String website;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "google_plus")
    private String googlePlus;
    @Column(name = "twitter")
    private String twitter;
    @Column(name = "linkedin")
    private String linkedin;

    public Location() {
    }

    public Location(Integer id) {
        this.id = id;
    }

    public Location(Integer id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
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
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.financegeorgia.entities.Location[ id=" + id + " ]";
    }
    
}

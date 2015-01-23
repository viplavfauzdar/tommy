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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Viplav
 */
@Entity
@Table(name = "business")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Business.findAll", query = "SELECT b FROM Business b"),
    @NamedQuery(name = "Business.findById", query = "SELECT b FROM Business b WHERE b.id = :id"),
    @NamedQuery(name = "Business.findByUserEmail", query = "SELECT b FROM Business b INNER JOIN User u WHERE b.userId = u.id and u.email = :email"),
    @NamedQuery(name = "Business.findByUserId", query = "SELECT b FROM Business b WHERE b.userId = :userId"),
    @NamedQuery(name = "Business.findByBusinessName", query = "SELECT b FROM Business b WHERE b.businessName = :businessName"),
    @NamedQuery(name = "Business.findBySummary", query = "SELECT b FROM Business b WHERE b.summary = :summary"),
    @NamedQuery(name = "Business.findByCategory", query = "SELECT b FROM Business b WHERE b.category = :category"),
    @NamedQuery(name = "Business.findByVideoUrl", query = "SELECT b FROM Business b WHERE b.videoUrl = :videoUrl"),
    @NamedQuery(name = "Business.findByImage1", query = "SELECT b FROM Business b WHERE b.image1 = :image1"),
    @NamedQuery(name = "Business.findByImage2", query = "SELECT b FROM Business b WHERE b.image2 = :image2"),
    @NamedQuery(name = "Business.findByImage3", query = "SELECT b FROM Business b WHERE b.image3 = :image3"),
    @NamedQuery(name = "Business.findByTargetAmount", query = "SELECT b FROM Business b WHERE b.targetAmount = :targetAmount"),
    @NamedQuery(name = "Business.findByEquityOffered", query = "SELECT b FROM Business b WHERE b.equityOffered = :equityOffered"),
    @NamedQuery(name = "Business.findByCashCommPercent", query = "SELECT b FROM Business b WHERE b.cashCommPercent = :cashCommPercent"),
    @NamedQuery(name = "Business.findByEquityCommPercent", query = "SELECT b FROM Business b WHERE b.equityCommPercent = :equityCommPercent"),
    @NamedQuery(name = "Business.findByApproved", query = "SELECT b FROM Business b WHERE b.approved = :approved")})
public class Business implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "summary")
    private String summary;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "image_1")
    private String image1;
    @Column(name = "image_2")
    private String image2;
    @Column(name = "image_3")
    private String image3;
    @Column(name = "target_amount")
    private Integer targetAmount;
    @Column(name = "equity_offered")
    private Integer equityOffered;
    @Column(name = "cash_comm_percent")
    private Integer cashCommPercent;
    @Column(name = "equity_comm_percent")
    private Integer equityCommPercent;
    @Column(name = "approved")
    private Short approved;

    public Business() {
    }

    public Business(Integer id) {
        this.id = id;
    }

    public Business(Integer id, int userId) {
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Integer getEquityOffered() {
        return equityOffered;
    }

    public void setEquityOffered(Integer equityOffered) {
        this.equityOffered = equityOffered;
    }

    public Integer getCashCommPercent() {
        return cashCommPercent;
    }

    public void setCashCommPercent(Integer cashCommPercent) {
        this.cashCommPercent = cashCommPercent;
    }

    public Integer getEquityCommPercent() {
        return equityCommPercent;
    }

    public void setEquityCommPercent(Integer equityCommPercent) {
        this.equityCommPercent = equityCommPercent;
    }

    public Short getApproved() {
        return approved;
    }

    public void setApproved(Short approved) {
        this.approved = approved;
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
        if (!(object instanceof Business)) {
            return false;
        }
        Business other = (Business) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.financegeorgia.entities.Business[ id=" + id + " ]";
    }
    
}

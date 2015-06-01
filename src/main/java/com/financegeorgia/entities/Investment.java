/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.financegeorgia.entities;

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
import org.eclipse.persistence.annotations.Cache;
import static org.eclipse.persistence.config.CacheIsolationType.ISOLATED;

/**
 *
 * @author Viplav
 */
@Entity
@Cache(isolation=ISOLATED) //to prevent caching since the date wasn't being being updated as it is inserted by the DB
@Table(name = "investment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Investment.findAll", query = "SELECT i FROM Investment i"),
    @NamedQuery(name = "Investment.findById", query = "SELECT i FROM Investment i WHERE i.id = :id"),
    @NamedQuery(name = "Investment.findByUserId", query = "SELECT i FROM Investment i WHERE i.userId = :userId"),
    @NamedQuery(name = "Investment.findByUserEmail", query = "SELECT i FROM Investment i INNER JOIN User u WHERE i.userId = u.id and u.email = :email"),
    @NamedQuery(name = "Investment.findByBusinessAndUserEmail", query = "SELECT i FROM Investment i INNER JOIN Business b ON i.businessId = b.id INNER JOIN User u ON b.userId = u.id WHERE u.email = :email"),
    @NamedQuery(name = "Investment.findByBusinessAndUserId", query = "SELECT i FROM Investment i INNER JOIN Business b WHERE i.businessId = b.id and b.userId = :userId"),
    @NamedQuery(name = "Investment.findSumInvestedByUserId", query = "SELECT i.userId, SUM(i.amountInvested) FROM Investment i WHERE i.userId = :userId GROUP BY i.userId"),
    @NamedQuery(name = "Investment.findByBusinessId", query = "SELECT i FROM Investment i WHERE i.businessId = :businessId"),
    @NamedQuery(name = "Investment.findSumInvestedByBusinessId", query = "SELECT i.businessId, SUM(i.amountInvested) FROM Investment i WHERE i.businessId = :businessId GROUP BY i.businessId"),
    @NamedQuery(name = "Investment.findByAmountInvested", query = "SELECT i FROM Investment i WHERE i.amountInvested = :amountInvested"),
    @NamedQuery(name = "Investment.findByEquityObtained", query = "SELECT i FROM Investment i WHERE i.equityObtained = :equityObtained"),
    @NamedQuery(name = "Investment.findByCommissionTaken", query = "SELECT i FROM Investment i WHERE i.commissionTaken = :commissionTaken")})
public class Investment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "business_id")
    private Integer businessId;
    @Column(name = "amount_invested")
    private Integer amountInvested;
    @Column(name = "equity_obtained")
    private Integer equityObtained;
    @Column(name = "commission_taken")
    private Integer commissionTaken;
    @Column(name = "investment_date", insertable = false, updatable = false) //added 5-20-15 because it started inserting NULL all of a sudden. Don't know why because it was working fine before
    @Temporal(TemporalType.TIMESTAMP)
    private Date investmentDate;

    public Investment() {
    }

    public Investment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(Integer amountInvested) {
        this.amountInvested = amountInvested;
    }

    public Integer getEquityObtained() {
        return equityObtained;
    }

    public void setEquityObtained(Integer equityObtained) {
        this.equityObtained = equityObtained;
    }

    public Integer getCommissionTaken() {
        return commissionTaken;
    }

    public void setCommissionTaken(Integer commissionTaken) {
        this.commissionTaken = commissionTaken;
    }
    
    //@JsonDateFormat("MM/dd/yyyy hh:mm:ss a")
    @JsonDateFormat("MMM dd, yyyy hh:mm a")
    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
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
        if (!(object instanceof Investment)) {
            return false;
        }
        Investment other = (Investment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Investment{" + "id=" + id + ", userId=" + userId + ", businessId=" + businessId + ", amountInvested=" + amountInvested + ", equityObtained=" + equityObtained + ", commissionTaken=" + commissionTaken + ", investmentDate=" + investmentDate + '}';
    }

    
    
    
    
}
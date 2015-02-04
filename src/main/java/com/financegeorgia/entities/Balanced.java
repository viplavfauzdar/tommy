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

/**
 *
 * @author Viplav
 */
@Entity
@Table(name = "balanced")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Balanced.findAll", query = "SELECT b FROM Balanced b"),
    @NamedQuery(name = "Balanced.findById", query = "SELECT b FROM Balanced b WHERE b.id = :id"),
    @NamedQuery(name = "Balanced.findByUserId", query = "SELECT b FROM Balanced b WHERE b.userId = :userId"),
    @NamedQuery(name = "Balanced.findByCustomerUri", query = "SELECT b FROM Balanced b WHERE b.customerUri = :customerUri"),
    @NamedQuery(name = "Balanced.findByBankAccountUri", query = "SELECT b FROM Balanced b WHERE b.bankAccountUri = :bankAccountUri")})
public class Balanced implements Serializable {
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "customer_uri")
    private String customerUri;
    @Basic(optional = false)
    @Column(name = "bank_account_uri")
    private String bankAccountUri;

    public Balanced() {
    }

    public Balanced(Integer id) {
        this.id = id;
    }

    public Balanced(Integer id, int userId, String customerUri, String bankAccountUri) {
        this.id = id;
        this.userId = userId;
        this.customerUri = customerUri;
        this.bankAccountUri = bankAccountUri;
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

    public String getCustomerUri() {
        return customerUri;
    }

    public void setCustomerUri(String customerUri) {
        this.customerUri = customerUri;
    }

    public String getBankAccountUri() {
        return bankAccountUri;
    }

    public void setBankAccountUri(String bankAccountUri) {
        this.bankAccountUri = bankAccountUri;
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
        if (!(object instanceof Balanced)) {
            return false;
        }
        Balanced other = (Balanced) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.financegeorgia.entities.Balanced[ id=" + id + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}

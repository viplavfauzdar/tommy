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
 * @author viplav
 */
@Entity
@Cache(isolation=ISOLATED)
@Table(name = "synapse_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SynapseUser.findAll", query = "SELECT s FROM SynapseUser s"),
    @NamedQuery(name = "SynapseUser.findById", query = "SELECT s FROM SynapseUser s WHERE s.id = :id"),
    @NamedQuery(name = "SynapseUser.findByFgUserId", query = "SELECT s FROM SynapseUser s WHERE s.fgUserId = :fgUserId"),
    @NamedQuery(name = "SynapseUser.findBySyUserId", query = "SELECT s FROM SynapseUser s WHERE s.syUserId = :syUserId"),
    @NamedQuery(name = "SynapseUser.findBySyUsername", query = "SELECT s FROM SynapseUser s WHERE s.syUsername = :syUsername"),
    @NamedQuery(name = "SynapseUser.findBySyRefreshToken", query = "SELECT s FROM SynapseUser s WHERE s.syRefreshToken = :syRefreshToken")})
public class SynapseUser implements Serializable {
    @Basic(optional = false)
    @Column(name = "sy_oauth_token")
    private String syOauthToken;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fg_user_id")
    private int fgUserId;
    @Basic(optional = false)
    @Column(name = "sy_user_id")
    private int syUserId;
    @Basic(optional = false)
    @Column(name = "sy_username")
    private String syUsername;
    @Basic(optional = false)
    @Column(name = "sy_refresh_token")
    private String syRefreshToken;

    public SynapseUser() {
    }

    public SynapseUser(Integer id) {
        this.id = id;
    }

    public SynapseUser(Integer id, int fgUserId, int syUserId, String syUsername, String syRefreshToken) {
        this.id = id;
        this.fgUserId = fgUserId;
        this.syUserId = syUserId;
        this.syUsername = syUsername;
        this.syRefreshToken = syRefreshToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFgUserId() {
        return fgUserId;
    }

    public void setFgUserId(int fgUserId) {
        this.fgUserId = fgUserId;
    }

    public int getSyUserId() {
        return syUserId;
    }

    public void setSyUserId(int syUserId) {
        this.syUserId = syUserId;
    }

    public String getSyUsername() {
        return syUsername;
    }

    public void setSyUsername(String syUsername) {
        this.syUsername = syUsername;
    }

    public String getSyRefreshToken() {
        return syRefreshToken;
    }

    public void setSyRefreshToken(String syRefreshToken) {
        this.syRefreshToken = syRefreshToken;
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
        if (!(object instanceof SynapseUser)) {
            return false;
        }
        SynapseUser other = (SynapseUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.financegeorgia.entities.SynapseUser[ id=" + id + " ]";
    }

    public String getSyOauthToken() {
        return syOauthToken;
    }

    public void setSyOauthToken(String syOauthToken) {
        this.syOauthToken = syOauthToken;
    }
    
}

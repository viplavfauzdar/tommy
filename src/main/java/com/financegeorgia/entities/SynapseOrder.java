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
@Table(name = "synapse_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SynapseOrder.findAll", query = "SELECT s FROM SynapseOrder s"),
    @NamedQuery(name = "SynapseOrder.findById", query = "SELECT s FROM SynapseOrder s WHERE s.id = :id"),
    @NamedQuery(name = "SynapseOrder.findBySyOrderId", query = "SELECT s FROM SynapseOrder s WHERE s.syOrderId = :syOrderId"),
    @NamedQuery(name = "SynapseOrder.findBySyAmount", query = "SELECT s FROM SynapseOrder s WHERE s.syAmount = :syAmount"),
    @NamedQuery(name = "SynapseOrder.findByFgIvstId", query = "SELECT s FROM SynapseOrder s WHERE s.fgIvstId = :fgIvstId")})
public class SynapseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "sy_order_id")
    private int syOrderId;
    @Basic(optional = false)
    @Column(name = "sy_amount")
    private int syAmount;
    @Basic(optional = false)
    @Column(name = "fg_ivst_id")
    private int fgIvstId;

    public SynapseOrder() {
    }

    public SynapseOrder(Integer id) {
        this.id = id;
    }

    public SynapseOrder(Integer id, int syOrderId, int syAmount, int fgIvstId) {
        this.id = id;
        this.syOrderId = syOrderId;
        this.syAmount = syAmount;
        this.fgIvstId = fgIvstId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSyOrderId() {
        return syOrderId;
    }

    public void setSyOrderId(int syOrderId) {
        this.syOrderId = syOrderId;
    }

    public int getSyAmount() {
        return syAmount;
    }

    public void setSyAmount(int syAmount) {
        this.syAmount = syAmount;
    }

    public int getFgIvstId() {
        return fgIvstId;
    }

    public void setFgIvstId(int fgIvstId) {
        this.fgIvstId = fgIvstId;
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
        if (!(object instanceof SynapseOrder)) {
            return false;
        }
        SynapseOrder other = (SynapseOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.financegeorgia.entities.SynapseOrder[ id=" + id + " ]";
    }
    
}

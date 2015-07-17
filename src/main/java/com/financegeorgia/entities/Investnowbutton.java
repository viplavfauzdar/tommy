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
 * @author viplav
 */
@Entity
@Table(name = "investnowbutton")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Investnowbutton.findAll", query = "SELECT i FROM Investnowbutton i"),
    @NamedQuery(name = "Investnowbutton.findById", query = "SELECT i FROM Investnowbutton i WHERE i.id = :id"),
    @NamedQuery(name = "Investnowbutton.findByBusId", query = "SELECT i FROM Investnowbutton i WHERE i.busId = :busId"),
    @NamedQuery(name = "Investnowbutton.findByScript", query = "SELECT i FROM Investnowbutton i WHERE i.script = :script"),
    @NamedQuery(name = "Investnowbutton.findByBtnAttr", query = "SELECT i FROM Investnowbutton i WHERE i.btnAttr = :btnAttr")})
public class Investnowbutton implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "bus_id")
    private Integer busId;
    @Column(name = "script")
    private String script;
    @Column(name = "btn_attr")
    private String btnAttr;

    public Investnowbutton() {
    }

    public Investnowbutton(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getBtnAttr() {
        return btnAttr;
    }

    public void setBtnAttr(String btnAttr) {
        this.btnAttr = btnAttr;
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
        if (!(object instanceof Investnowbutton)) {
            return false;
        }
        Investnowbutton other = (Investnowbutton) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.financegeorgia.entities.Investnowbutton[ id=" + id + " ]";
    }
    
}

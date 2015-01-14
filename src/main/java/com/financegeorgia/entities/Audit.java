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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Viplav
 */
@Entity
@Table(name = "audit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audit.findAll", query = "SELECT a FROM Audit a"),
    @NamedQuery(name = "Audit.findById", query = "SELECT a FROM Audit a WHERE a.id = :id"),
    @NamedQuery(name = "Audit.findByTableAndKey", query = "SELECT a FROM Audit a WHERE a.tableName = :tableName and a.tableKey = :tableKey"),
    @NamedQuery(name = "Audit.findByTableName", query = "SELECT a FROM Audit a WHERE a.tableName = :tableName"),
    @NamedQuery(name = "Audit.findByTableKey", query = "SELECT a FROM Audit a WHERE a.tableKey = :tableKey"),
    @NamedQuery(name = "Audit.findByCreateUid", query = "SELECT a FROM Audit a WHERE a.createUid = :createUid"),
    @NamedQuery(name = "Audit.findByUpdateUid", query = "SELECT a FROM Audit a WHERE a.updateUid = :updateUid"),
    @NamedQuery(name = "Audit.findByCreateTs", query = "SELECT a FROM Audit a WHERE a.createTs = :createTs"),
    @NamedQuery(name = "Audit.findByUpdateTs", query = "SELECT a FROM Audit a WHERE a.updateTs = :updateTs"),
    @NamedQuery(name = "Audit.findByCreateIp", query = "SELECT a FROM Audit a WHERE a.createIp = :createIp"),
    @NamedQuery(name = "Audit.findByUpdateIp", query = "SELECT a FROM Audit a WHERE a.updateIp = :updateIp"),
    @NamedQuery(name = "Audit.findByDeletedInd", query = "SELECT a FROM Audit a WHERE a.deletedInd = :deletedInd"),
    @NamedQuery(name = "Audit.findByComments", query = "SELECT a FROM Audit a WHERE a.comments = :comments")})
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "table_key")
    private Integer tableKey;
    @Column(name = "create_uid", insertable = true, updatable = false)
    private Integer createUid;
    @Column(name = "update_uid", insertable = false, updatable = true)
    private Integer updateUid;
    @Column(name = "create_ts", insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTs;
    @Column(name = "update_ts", insertable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTs;
    @Column(name = "create_ip", insertable = true, updatable = false)
    private String createIp;
    @Column(name = "update_ip", insertable = false, updatable = true)
    private String updateIp;
    @Column(name = "deleted_ind")
    private Short deletedInd;
    @Column(name = "comments")
    private String comments;

    public Audit() {
    }

    public Audit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTableKey() {
        return tableKey;
    }

    public void setTableKey(Integer tableKey) {
        this.tableKey = tableKey;
    }

    public Integer getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    public Integer getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    @JsonDateFormat("MM/dd/yyyy HH:mm:ss.SSS")
    public Date getCreateTs() {
        return createTs;
    }

    @JsonDateFormat("MM/dd/yyyy HH:mm:ss.SSS")
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    @JsonDateFormat("MM/dd/yyyy HH:mm:ss.SSS")
    public Date getUpdateTs() {
        return updateTs;
    }

    @JsonDateFormat("MM/dd/yyyy HH:mm:ss.SSS")
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
    }

    public Short getDeletedInd() {
        return deletedInd;
    }

    public void setDeletedInd(Short deletedInd) {
        this.deletedInd = deletedInd;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        if (!(object instanceof Audit)) {
            return false;
        }
        Audit other = (Audit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    private final static Genson genson = new Genson();
    @Override
    public String toString() {
        return "com.financegeorgia.entities.Audit[ " + genson.serialize(this) + " ]";
    }

    @PrePersist
    protected void onCreate() {
        //this.createTs = new Date();
        setCreateTs(new Date());
    }

    @PreUpdate
    protected void onUpdate() {
        //this.updateTs = new Date();
        setUpdateTs(new Date());
    }

}

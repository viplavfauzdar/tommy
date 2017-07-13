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
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findById", query = "SELECT d FROM Document d WHERE d.id = :id"),
    @NamedQuery(name = "Document.findByUserId", query = "SELECT d FROM Document d WHERE d.userId = :userId"),
    @NamedQuery(name = "Document.findByBusinessId", query = "SELECT d FROM Document d WHERE d.businessId = :businessId"),
    @NamedQuery(name = "Document.findByDocumentKey", query = "SELECT d FROM Document d WHERE d.documentKey = :documentKey"),
    @NamedQuery(name = "Document.findByDocument", query = "SELECT d FROM Document d WHERE d.document = :document"),
    @NamedQuery(name = "Document.findByDownloadedDocument", query = "SELECT d FROM Document d WHERE d.downloadedDocument = :downloadedDocument"),
    @NamedQuery(name = "Document.findByDocumentscol", query = "SELECT d FROM Document d WHERE d.documentscol = :documentscol")})
public class Document implements Serializable {
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
    @Column(name = "document_key")
    private String documentKey;
    @Column(name = "document")
    private String document;
    @Column(name = "downloaded_document")
    private String downloadedDocument;
    @Column(name = "documentscol")
    private String documentscol;

    public Document() {
    }

    public Document(Integer id) {
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

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        this.documentKey = documentKey;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDownloadedDocument() {
        return downloadedDocument;
    }

    public void setDownloadedDocument(String downloadedDocument) {
        this.downloadedDocument = downloadedDocument;
    }

    public String getDocumentscol() {
        return documentscol;
    }

    public void setDocumentscol(String documentscol) {
        this.documentscol = documentscol;
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
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.financegeorgia.entities.Document[ id=" + id + " ]";
    }
    
}

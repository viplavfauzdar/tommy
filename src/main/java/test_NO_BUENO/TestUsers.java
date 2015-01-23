/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_NO_BUENO;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Viplav
 * 
 * tested with separate user-roles tables and generated code with roles collection. doesn't return roles as nested within users
 */
@Entity
@Table(name = "test_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TestUsers.findAll", query = "SELECT t FROM TestUsers t"),
    @NamedQuery(name = "TestUsers.findById", query = "SELECT t FROM TestUsers t WHERE t.id = :id"),
    @NamedQuery(name = "TestUsers.findByUsername", query = "SELECT t FROM TestUsers t WHERE t.username = :username")})
public class TestUsers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private Collection<TestRoles> testRolesCollection;

    public TestUsers() {
    }

    public TestUsers(Integer id) {
        this.id = id;
    }

    public TestUsers(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public Collection<TestRoles> getTestRolesCollection() {
        return testRolesCollection;
    }

    public void setTestRolesCollection(Collection<TestRoles> testRolesCollection) {
        this.testRolesCollection = testRolesCollection;
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
        if (!(object instanceof TestUsers)) {
            return false;
        }
        TestUsers other = (TestUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.TestUsers[ id=" + id + " ]";
    }
    
}

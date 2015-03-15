/**
 * Class Pending utilizes Java's Persistence API
 * to map the User table to this class
 * 
 * @author Paul Soiya II psoiya@asu.edu
 * @version 1/18/2015
 */
package com.gm.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity @Table(name="PENDING_USER") @XmlRootElement
public class PendingUser implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id 
    private Long id;
    
    private String email;
    
    private String password;
    
    private String salt;
    
    @Column(name="FNAME")
    private String firstName;
    
    @Column(name="LNAME")
    private String lastName;

    private String position;
    
    private String reason;
    
    @Column(name = "CREATE_DATE", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();
    
    public PendingUser() { } 
    
    public PendingUser(Long id, String email, String password, String salt, 
                       String position, String reason,
                       String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.position = position;
        this.reason = reason;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PendingUser(String firstName, String lastName, String email, 
                        String password, String salt, 
                       String position, String reason) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.position = position;
        this.reason = reason;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        if (!(object instanceof PendingUser)) {
            return false;
        }
        PendingUser other = (PendingUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}

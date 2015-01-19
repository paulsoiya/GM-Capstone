/**
 * Class User utilizes TOPLINK's java's persistence API
 * to map the User table to the User class 
 * 
 * @author Paul Soiya II psoiya@asu.edu
 * @version 1/17/2015
 */
package com.gm.user;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;

 
@Entity 
public class User implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String email;
    
    private String password;
    
    private String salt;
    
    @Column(name="is_admin")
    private boolean admin;
    
    @Column(name = "fname")
    private String firstName;
    
    @Column(name = "lname")
    private String lastName;
    
    @Column(name = "create_date") 
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createDate;

    public User() { }
   
    public User(long id, String email, String password, String salt, 
            boolean admin, String firstName, String lastName, Timestamp createDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.admin = admin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createDate = createDate;
    }

    public User(String email, String password, String salt, boolean admin,
            String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.admin = admin;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    
}

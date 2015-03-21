/**
 * Class Makes utilizes TOPLINK's java's persistence API
 * to map the FilterQuery table to the FilterQuery class 
 * 
 * @author Becca Little
 * @version 3/21/2015
 */
package com.gm.car;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;


@Entity @XmlRootElement 
@Table(name="filterquerymakes")
public class Makes implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name="filterid")
    @Id @GeneratedValue
    private long id;
    
    private String name;
    
    private String models;

    public Makes() { }
   
    public Makes(long id, String name, String models) {
        this.id = id;
        this.name = name;
        this.models = models;
    }  
    
  public Makes(String name, String models) {
        this.name = name;
        this.models = models;
    }  
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModels() {
        return models;
    }

    public void setModles(String models) {
        this.models = models;
    }
    
}

/**
 * Class Makes utilizes TOPLINK's java's persistence API
 * to map the Makes table to the Makes class 
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
@Table(name="makes")
public class Makes implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name="make_id")
    @Id
    private long makeId;
    
    @Column(name="make_name")
    private String makeName;

    public Makes() { }
   
    public Makes(long makeId, String makeName) {
        this.makeId = makeId;
        this.makeName = makeName;
    }
  
    public Makes(String makeName) {
        this.makeName = makeName;
    }
    
    public long getMakeId() {
        return makeId;
    }

    public void setMakeId(long makeId) {
        this.makeId = makeId;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }
    
}

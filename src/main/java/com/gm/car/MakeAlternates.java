/**
 * Class ModelAlternates utilizes TOPLINK's java's persistence API
 * to map the MakeAlternates table to the MakeAlternates class 
 * 
 * @author Becca Little
 * @version 4/12/2015
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
@Table(name="make_alternates")
public class MakeAlternates implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column (name="make_alternate_id")
    @Id
    private long makeAlternateId;             
  
    @Column(name="make_id")
    private long makeId;
    
    @Column(name="make_alternate_name")
    private String makeAlternateName;

    public MakeAlternates() { }
   
    public MakeAlternates(long makeAlternateId, long makeId, String makeAlternateName) {
        this.makeAlternateId = makeAlternateId;
        this.makeId = makeId;
        this.makeAlternateName = makeAlternateName;
    }
  
    public MakeAlternates(long makeId, String makeAlternateName) {
        this.makeId = makeId;
        this.makeAlternateName = makeAlternateName;
    }
    
    public void setMakeAlternateId(long makeAlternateId) {
        this.makeAlternateId = makeAlternateId;
    }
  
    public long getMakeAlternateId() {
        return makeAlternateId;
    }

    public void setMakeId(long makeId) {
        this.makeId = makeId;
    }
  
    public long getMakeId() {
        return makeId;
    }

    public String getMakeAlternateName() {
        return makeAlternateName;
    }

    public void setMakeAlternateName(String makeAlternateName) {
        this.makeAlternateName = makeAlternateName;
    }
    
}

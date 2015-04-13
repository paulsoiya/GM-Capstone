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
    
    @Column(name="make_id")
    @Id
    private int makeId;
    
    @Column(name="make_alternate")
    private String makeAlternate;

    public MakeAlternates() { }
   
    public MakeAlternates(int makeId, String makeAlternate) {
        this.makeId = makeId;
        this.makeAlternate = makeAlternate;
    }
    
    public int getMakeId() {
        return makeId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public String getMakeAlternate() {
        return makeAlternate;
    }

    public void setMakeAlternate(String makeAlternate) {
        this.makeAlternate = makeAlternate;
    }
    
}

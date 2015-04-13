/**
 * Class ExplicitContent utilizes TOPLINK's java's persistence API
 * to map the ExcplicitContent table to the ExplicitContent class 
 * 
 * @author Becca Little
 * @version 4/12/2015
 */
package com.gm.analytics;

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
@Table(name="explicit_content")
public class ExplicitContent implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id 
    private long id;
    
    @Column(name="explicit_words")
    private String explicitWords;
 
    public ExplicitContent() { }
   
    public ExplicitContent(long id, String explicitWords) {
        this.id = id;
        this.explicitWords = explicitWords;
    }

    public ExplicitContent(String explicitWords) {
        this.explicitWords = explicitWords;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExplicitWords() {
        return explicitWords;
    }

    public void setExplicitWords(String explicitWords) {
        this.explicitWords = explicitWords;
    }

}

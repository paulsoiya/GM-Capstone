/**
 * Class CommonContent utilizes TOPLINK's java's persistence API
 * to map the CommonContent table to the CommonContent class 
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
@Table(name="common_content")
public class CommonContent implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id 
    private long id;
    
    @Column(name="common_words")
    private String commonWords;
 
    public CommonContent() { }
   
    public CommonContent(long id, String commonWords) {
        this.id = id;
        this.commonWords = commonWords;
    }

    public CommonContent(String commonWords) {
        this.commonWords = commonWords;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommonWords() {
        return commonWords;
    }

    public void setCommonWords(String commonWords) {
        this.commonWords = commonWords;
    }

}

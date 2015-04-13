/**
 * Class ModelAlternates utilizes TOPLINK's java's persistence API
 * to map the ModelAlternates table to the ModelAlternates class 
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
@Table(name="model_alternates")
public class ModelAlternates implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name="model_id")
    @Id
    private int modelId;
    
    @Column(name="model_alternate")
    private String modelAlternate;

    public ModelAlternates() { }
   
    public ModelAlternates(int modelId, String modelAlternate) {
        this.modelId = modelId;
        this.modelAlternate = modelAlternate;
    }
    
    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelAlternate() {
        return modelAlternate;
    }

    public void setModelAlternate(String modelAlternate) {
        this.modelAlternate = modelAlternate;
    }
    
}

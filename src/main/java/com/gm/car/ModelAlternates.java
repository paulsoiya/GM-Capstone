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
    
    @Column(name="model_alternate_id")
    @Id
    private long modelAlternateId;
  
    @Column(name="model_id")
    private long modelId;
    
    @Column(name="model_alternate_name")
    private String modelAlternateName;

    public ModelAlternates() { }
  
    public ModelAlternates(long modelAlternateId, long modelId, String modelAlternateName) {
        this.modelAlternateId = modelAlternateId;
        this.modelId = modelId;
        this.modelAlternateName = modelAlternateName;
    }
   
    public ModelAlternates(long modelId, String modelAlternateName) {
        this.modelId = modelId;
        this.modelAlternateName = modelAlternateName;
    }
    
    public long getModelAlternateId() {
        return modelAlternateId;
    }

    public void setModelAlternateId(long modelAlternateId) {
        this.modelAlternateId = modelAlternateId;
    }
    
    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getModelAlternateName() {
        return modelAlternateName;
    }

    public void setModelAlternateName(String modelAlternateName) {
        this.modelAlternateName = modelAlternateName;
    }
    
}

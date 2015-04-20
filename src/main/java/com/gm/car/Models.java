/**
 * Class Models utilizes TOPLINK's java's persistence API
 * to map the Models table to the Models class 
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
@Table(name="models")
public class Models implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name="model_id")
    @Id
    private long modelId;    
  
    @Column(name="make_id")
    private long makeId;
    
    @Column(name="model_name")
    private String modelName;

    public Models() { }
   
    public Models(long modelId, long makeId, String modelName) {
        this.modelId = modelId;
        this.makeId = makeId;
        this.modelName = modelName;
    }
  
    public Models(long makeId, String modelName) {
        this.makeId = makeId;
        this.modelName = modelName;
    }
    
    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }
  
    public long getMakeId() {
        return makeId;
    }

    public void setMakeId(long makeId) {
        this.makeId = makeId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
}

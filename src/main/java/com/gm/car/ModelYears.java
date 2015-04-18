/**
 * Class ModelYears utilizes TOPLINK's java's persistence API
 * to map the ModelYears table to the ModelYears class 
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
@Table(name="model_years")
public class ModelYears implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Column(name="year_id")
    @Id
    private long yearId;
  
    @Column(name="model_id")
    private long modelId;
    
    @Column(name="year_name")
    private String yearName;

    public ModelYears() { }
   
    public ModelYears(long yearId, long modelId, String yearName) {
        this.yearId = yearId;
        this.modelId = modelId;
        this.yearName = yearName;
    }
  
    public ModelYears(long modelId, String yearName) {
        this.modelId = modelId;
        this.yearName = yearName;
    }
    
    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }
    
}

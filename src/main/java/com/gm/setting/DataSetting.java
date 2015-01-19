/**
 * Class DataSetting utilizes Java's Persistence API
 * to map the data_setting table to this class
 * 
 * @author Paul Soiya II psoiya@asu.edu
 * @version 1/18/2015
 */
package com.gm.setting;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity @Table(name = "DATA_SETTING")
public class DataSetting implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="pull_frequency")
    private int pullFrequency;
    
    @Column(name="data_retention")
    private int dataRetention;

    public DataSetting() { }
    
    public DataSetting(Long id, int pullFrequency, int dataRetention) {
        this.id = id;
        this.pullFrequency = pullFrequency;
        this.dataRetention = dataRetention;
    }
    
    public DataSetting(int pullFrequency, int dataRetention){
        this.pullFrequency = pullFrequency;
        this.dataRetention = dataRetention;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPullFrequency() {
        return pullFrequency;
    }

    public void setPullFrequency(int pullFrequency) {
        this.pullFrequency = pullFrequency;
    }

    public int getDataRetention() {
        return dataRetention;
    }

    public void setDataRetention(int dataRetention) {
        this.dataRetention = dataRetention;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataSetting)) {
            return false;
        }
        DataSetting other = (DataSetting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}

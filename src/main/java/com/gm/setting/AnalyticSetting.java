/**
 * Utilizes Java's Persistence API
 * to map the analytic_setting table to this class
 * 
 * @author Paul Soiya II psoiya@asu.edu
 * @version 1/18/2015
 *          3/11/2015 - Modified by Vance Anderson
 */
package com.gm.setting;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity @Table(name = "analytic_setting")
public class AnalyticSetting implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "data_access")
    private int dataAccess;
    
    @Column(name = "top_words")
    private int topWords;

    @Column(name = "explicit_words")
    private String explicitWords;

    public AnalyticSetting(String explicitWords) {
        this.explicitWords = explicitWords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(int dataAccess) {
        this.dataAccess = dataAccess;
    }

    public int getTopWords() {
        return topWords;
    }

    public void setTopWords(int topWords) {
        this.topWords = topWords;
    }

    public String getExplicitWords() {
        return explicitWords;
    }

    public void setExplicitWords(String explicitWords) {
        this.explicitWords = explicitWords;
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
        if (!(object instanceof AnalyticSetting)) {
            return false;
        }
        AnalyticSetting other = (AnalyticSetting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gm.setting.AnalyticSetting[ id=" + id + " ]";
    }
    
}

package com.gm.message;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReturnMessage implements Serializable{
    
    public String result;
    
    public ReturnMessage() { }
    
    public ReturnMessage(String result){
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
}

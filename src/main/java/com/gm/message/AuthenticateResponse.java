package com.gm.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class AuthenticateResponse {
    
    private boolean result;
    private long id;
    private boolean admin;
    private String firstName; 
    
    public AuthenticateResponse() { }
    
    public AuthenticateResponse(boolean result, long id, boolean admin, String firstName){
        this.result = result;
        this.id = id;
        this.admin = admin;
        this.firstName = firstName;
    }
    
    public AuthenticateResponse(boolean result){
      this.result = result;
    }
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
    
}

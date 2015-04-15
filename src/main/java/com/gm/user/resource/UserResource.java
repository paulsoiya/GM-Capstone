/**
 * JAX-RS RESTful implementation for the User Entity
 * 
 * @author Paul Soiya II
 * @version 3/7/2015
 */
package com.gm.user.resource;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import java.util.Date;
import java.util.List;

import com.gm.user.PendingUser;
import com.gm.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gm.message.AuthenticateResponse;
import com.gm.message.ReturnMessage;
import com.gm.security.SecurityHelper;

@Stateless
@LocalBean
@Path("/users")
public class UserResource {
    
    @PersistenceContext(unitName="mydb")
    EntityManager em;
    
    @GET @Produces("application/json")
    public List<User> getAll(){
       
        Query query = em.createQuery("SELECT u from User u", User.class);
        
        return query.getResultList();
    }
   
    @GET
    @Produces("application/json")
    public User findUser(@PathParam("id") long id){
    	User user = em.find(User.class, id);
    	
    	return user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public ReturnMessage createUser(@FormParam("email") String email,
    								@FormParam("password") String password,
    								@FormParam("first_name") String firstName,
    								@FormParam("last_name") String lastName,
    								@FormParam("puser_id") long id){
        
       User user = new User(email, password, false, firstName, lastName); 
       em.persist(user);

       ReturnMessage rm = new ReturnMessage();
       //check if object was persisted and return
       //appropriate result message
       if (em.contains(user)) {
           rm.setResult("success");
       } else {
           rm.setResult("fail");
       }
       
       //remove the pending user record of the new user
       em.remove( em.find(PendingUser.class, id));

       return rm;
    }
    
    @POST
    @Path("/authenticate") 
    @Produces("application/json")
    public AuthenticateResponse login(@FormParam("email") String email,
                                     @FormParam("password") String password){
        
        SecurityHelper sh = new SecurityHelper();
        
        String md5Password = sh.md5(password);
        
        Query q = em.createNamedQuery("findUserWithEmail").setParameter("email",
                    email);
        
        Boolean result = false;
        User user = null;
      
        List<User> users = q.getResultList();
        //check if the user exists
        if(!users.isEmpty()){
            user = (User) users.get(0);
            
            //check if the user entered the correct email address and password
            if ( (user.getEmail()).equalsIgnoreCase(email) &&
                      (user.getPassword()).equals(md5Password)) { 
                result = true;
            }
            
        }
   
        AuthenticateResponse response;
        if(result){
            response = new AuthenticateResponse(result,
                                                user.getId(), user.isAdmin());
        }else{
            response = new AuthenticateResponse(result);
        }
        
        return response;
    }
    
    @DELETE @Path("/{id}")
    public void deleteUser(@PathParam("id") long id){
        em.remove(em.find(User.class, id));
    }
    
    @PUT @Produces("application/json") @Path("/{id}")
    public ReturnMessage updateUser(@PathParam("id") long id,
    					   @FormParam("first_name") String firstName,
    					   @FormParam("last_name") String lastName,
    					   @FormParam("email") String email,
    					   @FormParam("password") String password){
        User user = em.find(User.class, id);
        
        ReturnMessage rm = new ReturnMessage();
        
        if ( user == null ) {
        	rm.setResult("failure");
        } else {
        	
        	SecurityHelper sh = new SecurityHelper();
            String md5Password = sh.md5(password); //encrypt the password
        
        	user.setFirstName(firstName);
        	user.setLastName(lastName);
        	user.setEmail(email);
        	user.setPassword(md5Password);
        	
        	em.persist(user);
        	
        	rm.setResult("success");
        	
        }
        
        return rm;
       
    }

	@PUT
	@Produces("application/json")
	@Path("/{id}/role")
	public ReturnMessage makeAdmin(@PathParam("id") long id,
								   @FormParam("role") boolean role) {
		
		User user = em.find(User.class, id);
		ReturnMessage rm = new ReturnMessage();
		
		if ( user == null ) {
			rm.setResult("failure");
		} else { 
			user.setAdmin(role);
			em.persist(user);
			rm.setResult("success");
		}

		return rm;
	}
    
}

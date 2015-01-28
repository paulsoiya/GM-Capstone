/**
 * JAX-RS RESTful implementation for the PendingUser Entity
 * 
 * @author Paul Soiya II
 * @version 1/18/2015
 */
package com.gm.user.resource;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import com.gm.user.PendingUser;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
@Path("/pending-users")
public class PendingUserResource {
    
    @PersistenceContext(unitName="mydb")
    EntityManager em;
    
    @GET @Produces("application/json")
    public List<PendingUser> getAll(){
       
        Query query = em.createQuery("SELECT p from PendingUser p",
                        PendingUser.class);
        
        return query.getResultList();
        
    }
   
    @POST 
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createUser(@FormParam("first_name") String firstName,
                           @FormParam("last_name") String lastName,
                           @FormParam("email") String email,
                           @FormParam("password") String password,
                           @FormParam("position") String position,
                           @FormParam("reason") String reason){
        
       String salt = ""; // will be replaced with hashing function output 
      
       PendingUser p = new PendingUser(firstName, lastName, email,
                                        password, salt, position, reason);
       em.persist(p);
    }

    @DELETE @Path("/{id}")
    public void deleteUser(@PathParam("id") long id){
        em.remove(em.find(PendingUser.class, id));
    }
    
    @PUT @Path("/{id}")
    public void updateUser(@PathParam("id") long id){
        
    }
    
}

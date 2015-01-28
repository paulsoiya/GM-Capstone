/**
 * JAX-RS RESTful implementation for the User Entity
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
import java.util.List;

import com.gm.user.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
   
    @POST @Produces("application/json")
    public void createUser(@QueryParam("email") String email,
                           @QueryParam("password") String password,
                           @QueryParam("first_name") String firstName,
                           @QueryParam("last_name") String lastName){
        
        
       String salt = ""; // this will be replaced by the hashing function
       User user = new User(email, password, salt, false, firstName, lastName); 
        
       em.persist(user);
    }
    
    @DELETE @Path("/{id}")
    public void deleteUser(@PathParam("id") long id){
        em.remove(em.find(User.class, id));
    }
    
    @PUT @Path("/{id}")
    public void updateUser(@PathParam("id") long id){
        
    }
    
}

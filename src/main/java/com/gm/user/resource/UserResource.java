/**
 * JAX-RS RESTful implementation for the User Entity
 * 
 * @author Paul Soiya II
 * @version 1/18/2015
 */
package com.gm.user.resource;

import javax.ejb.EJB;
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
import com.gm.user.service.UserDao;

@Path("/api/user")
public class UserResource {
    
    @EJB
    private UserDao userDao;
    
    @GET @Produces("application/json")
    public List<User> getAll(){
        return userDao.retrieveAllUsers();
    }
    
    @POST @Produces("application/json")
    public User createUser(@QueryParam("email") String email,
                           @QueryParam("password") String password,
                           @QueryParam("first_name") String firstName,
                           @QueryParam("last_name") String lastName){
        
       return userDao.createUser(email, password, false, firstName,
                                 lastName);
    }
    
    @DELETE @Path("/{id}")
    public void deleteUser(@PathParam("id") long id){
        userDao.deleteUser(id);
    }
    
}

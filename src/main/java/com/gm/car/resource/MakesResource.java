/**
 * JAX-RS RESTful implementation for the Makes Entity
 * 
 * @author Becca Little
 * @version 4/12/2015
 */
package com.gm.car.resource;

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

import java.util.List;

import com.gm.car.Makes;
import com.gm.message.ReturnMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
@Path("/makes")
public class MakesResource {
    
    @PersistenceContext(unitName="mydb")
    EntityManager em;
    
    @GET @Produces("application/json")
    public List<Makes> getAll(){
       
        Query query = em.createQuery("SELECT m from Makes m", Makes.class);
        
        return query.getResultList();
    }
  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public ReturnMessage createMake(@FormParam("makeName") String makeName){
        
       Makes make = new Makes(makeName); 
       em.persist(make);

       ReturnMessage rm = new ReturnMessage();
       //check if object was persisted and return
       //appropriate result message
       if (em.contains(make)) {
           rm.setResult("success");
       } else {
           rm.setResult("fail");
       }

       return rm;
    }
  
    @DELETE @Path("/{id}")
    public void deleteMake(@PathParam("id") long id){
        em.remove(em.find(Makes.class, id));
    }
}

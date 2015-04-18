/**
 * JAX-RS RESTful implementation for the Models Entity
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

import com.gm.car.Models;
import com.gm.message.ReturnMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
@Path("/models")
public class ModelsResource {
    
    @PersistenceContext(unitName="mydb")
    EntityManager em;
    
    @GET @Produces("application/json")
    public List<Models> getAll(){
       
        Query query = em.createQuery("SELECT m from Models m", Models.class);
        
        return query.getResultList();
    }
  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public ReturnMessage createModel(@FormParam("makeId") long makeId,
                                     @FormParam("modelName") String modelName){
        
       Models model = new Models(makeId, modelName); 
       em.persist(model);

       ReturnMessage rm = new ReturnMessage();
       //check if object was persisted and return
       //appropriate result message
       if (em.contains(model)) {
           rm.setResult("success");
       } else {
           rm.setResult("fail");
       }

       return rm;
    }
  
    @DELETE @Path("/{id}")
    public void deleteModel(@PathParam("id") long id){
        em.remove(em.find(Models.class, id));
    }
}

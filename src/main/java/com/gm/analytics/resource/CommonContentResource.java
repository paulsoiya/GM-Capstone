/**
 * JAX-RS RESTful implementation for the CommonContent Entity
 * 
 * @author Becca Little
 * @version 4/12/2015
 */
package com.gm.analytics.resource;

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

import com.gm.analytics.CommonContent;
import com.gm.message.ReturnMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
@Path("/common-content")
public class CommonContentResource {
    
    @PersistenceContext(unitName="mydb")
    EntityManager em;
    
    @GET @Produces("application/json")
    public List<CommonContent> getAll(){
       
        Query query = em.createQuery("SELECT c from CommonContent c", CommonContent.class);
        
        return query.getResultList();
    }
  
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public ReturnMessage createExplicit(@FormParam("commonWords") String commonWords){
        
       CommonContent common = new CommonContent(commonWords); 
       em.persist(common);

       ReturnMessage rm = new ReturnMessage();
       //check if object was persisted and return
       //appropriate result message
       if (em.contains(common)) {
           rm.setResult("success");
       } else {
           rm.setResult("fail");
       }

       return rm;
    }
  
    @DELETE @Path("/{id}")
    public void deleteWord(@PathParam("id") long id){
        em.remove(em.find(CommonContent.class, id));
    }
    
}

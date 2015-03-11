
/**
 * JAX-RS RESTful implementation for the Analytic Setting Entity
 * 
 * @author Vance Anderson
 * @version 3/11/2015
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
import javax.ws.rs.FormParam;
import java.util.List;

import com.gm.user.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
@Path("/analytics")
public class AnalyticResource {
    
    @PersistenceContext(unitName="mydb")
    EntityManager em;
    
    @GET @Produces("application/json")
    public List<User> getAll(){
       
        Query query = em.createQuery("SELECT s from Analytic_Setting s", AnalyticSetting.class);
        
        return query.getResultList();
        
    }
   
    @POST @Produces("application/json")
    public void createExplicitLit(@QueryParam("explicit_words") String explicitWords){
       AnalyticSetting setting = new AnalyticSetting(explicitWords); 
        
       em.persist(setting);
    }
    
    @DELETE @Path("/{id}")
    public void deleteSetting(@PathParam("id") long id){
        em.remove(em.find(AnalyticSetting.class, id));
    }
    
    @PUT @Path("/{id}")
    public void updateUser(@PathParam("id") long id){
        
    }
    
}

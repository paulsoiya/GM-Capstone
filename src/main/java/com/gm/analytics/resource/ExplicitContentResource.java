/**
 * JAX-RS RESTful implementation for the ExplicitContent Entity
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

import com.gm.analytics.ExplicitContent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
@Path("/explicit-content")
public class ExplicitContentResource {
    
    @PersistenceContext(unitName="mydb")
    EntityManager em;
    
    @GET @Produces("application/json")
    public List<ExplicitContent> getAll(){
       
        Query query = em.createQuery("SELECT e from ExplicitContent e", ExplicitContent.class);
        
        return query.getResultList();
    }
    
}

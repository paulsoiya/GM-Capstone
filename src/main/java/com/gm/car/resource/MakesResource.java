/**
 * JAX-RS RESTful implementation for the Makes Entity
 * 
 * @author Becca Little
 * @version 3/21/2015
 */
package com.gm.car.resource;

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

import com.gm.car.Makes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.gm.message.AuthenticateResponse;
import com.gm.security.SecurityHelper;

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
}

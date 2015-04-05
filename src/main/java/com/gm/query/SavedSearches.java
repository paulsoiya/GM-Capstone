package com.gm.query;

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
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gm.user.PendingUser;
import com.gm.message.ReturnMessage;
import com.gm.car.Makes;
import com.gm.car.Models;
import com.gm.car.resource.MakesResource;
import com.gm.car.resource.ModelsResource;

import java.nio.charset.Charset;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.gm.security.SecurityHelper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







@Stateless
@LocalBean
@Path("/savedsearches")
public class SavedSearches {


	@PersistenceContext(unitName="mydb")
    EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public String getSavedViews( @FormParam("user") String user ){
		
		JSONArray queryList = new JSONArray();
		
		System.out.println(user);
		
		CouchConnection couch = new CouchConnection("http://localhost:5984/", "gm/");
		
		
		Query query = em.createQuery("SELECT m from Makes m", Makes.class);
		List<Makes> makes = query.getResultList();
		Query query1 = em.createQuery("SELECT m from Models m", Models.class);
		List<Models> models = query1.getResultList();
		
		for(int i = 0; i < makes.size(); ++i){
			System.out.println(makes.get(i).getMakeName());
			String makedb = makes.get(i).getMakeName().toLowerCase();
			couch = new CouchConnection("http://localhost:5984/", makedb+"/");
			try{
				String view = couch.queryDB("_design/"+user);
				if(view != null){
					queryList.put(makedb);
				}
			}catch(Exception e){
				System.out.println("not found");
			}
		}
		
		for(int i = 0; i < models.size(); ++i){
			System.out.println(models.get(i).getModelName());
			String modeldb = models.get(i).getModelName().toLowerCase();
			couch = new CouchConnection("http://localhost:5984/", modeldb+"/");
			try{
				String view = couch.queryDB("_design/"+user);
				if(view != null){
					queryList.put(modeldb);
					
				}
			}catch(Exception e){
				System.out.println("not found");
			}
		}
		ReturnMessage rm = new ReturnMessage();
		rm.setResult(queryList.toString());
        return queryList.toString();
    }
	
	
	
}

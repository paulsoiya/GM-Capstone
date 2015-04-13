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
    public String saveSearch( @FormParam("user") String user,
	@FormParam("endDate") String endDate,
	@FormParam("startDate") String startDate,
	@FormParam("make") String make,
	@FormParam("model") String model,
	@FormParam("year") String year, 
	@FormParam("searchName") String searchName ){
		
		JSONArray queryList = new JSONArray();
		
		System.out.println(user);
		
		CouchConnection couch = new CouchConnection("http://localhost:5984/", "savedsearches/");
		
		String db = "gm";
		
		if(!make.equals("undefined")){
			db = make.toLowerCase();
		}
		if(!model.equals("undefined")){
			db = model.toLowerCase();
		}
		if(!year.equals("undefined")){
			db = model.toLowerCase()+year;
		}
		
		
		JSONObject viewDocument = new JSONObject();
		
		boolean existingViews = true;
		try{
			viewDocument = new JSONObject(couch.queryDB(user));
			JSONObject search = new JSONObject();
			search.put("viewName", "_design/("+startDate+")("+endDate+")");
			search.put("db", db);
			viewDocument.put(searchName, search);
			couch.updateDocument(user,viewDocument);
		}
		catch(Exception e){
			existingViews = false;
		}

		if(!existingViews){
			viewDocument.put("_id", user);
			JSONObject search = new JSONObject();
			search.put("viewName", "_design/("+startDate+")("+endDate+")");
			search.put("db", db);
			viewDocument.put(searchName, search);
			couch.createDocuments(viewDocument, false);	
		}
		return "success";
    }
	
	
    @POST @Path("/getSavedSearches")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public String getSavedSearches( @FormParam("user") String user ){
		
		JSONArray queryList = new JSONArray();
		
		System.out.println(user);
		
		CouchConnection couch = new CouchConnection("http://localhost:5984/", "savedsearches/");
		
		JSONObject viewDocument = new JSONObject();
		JSONArray searches = new JSONArray();
		JSONArray keys = new JSONArray();
		boolean existingViews = true;
		try{
			viewDocument = new JSONObject(couch.queryDB(user));
			keys = viewDocument.names();
			System.out.println(keys.toString());
		}
		catch(Exception e){
			existingViews = false;
		}
		
		return keys.toString();
    }	
	
    @POST @Path("/getSavedSearch")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public String getSavedSearch( @FormParam("user") String user,
	@FormParam("searchName") String searchName ){
		
		JSONArray queryList = new JSONArray();
		
		System.out.println(user);
		
		CouchConnection couch = new CouchConnection("http://localhost:5984/", "savedsearches/");
		
		JSONObject viewDocument = new JSONObject();
		JSONObject search = new JSONObject();
		JSONObject returnJSON = new JSONObject();
		String response = "error";
		try{
			viewDocument = new JSONObject(couch.queryDB(user));
			search = (JSONObject)viewDocument.get(searchName);
			String viewName = (String)search.get("viewName");
			String viewDBString = (String)search.get("db");
			System.out.println(viewDBString);
			CouchConnection viewDB = new CouchConnection("http://localhost:5984/", viewDBString+"/");
			returnJSON.put("wordCount", viewDB.queryDB(viewName+"/_view/wordCount?group=true"));
			returnJSON.put("sentiment", viewDB.queryDB(viewName+"/_view/sentiment"));
			response = returnJSON.toString();
		}
		catch(Exception e){
			response = "error";
		}
		
		return response;
    }		
}

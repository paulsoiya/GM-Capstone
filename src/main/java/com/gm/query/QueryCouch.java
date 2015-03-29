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
@Path("/query")
public class QueryCouch {


    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public ReturnMessage createUser(@FormParam("location") String location,
	@FormParam("endDate") String endDate,
	@FormParam("startDate") String startDate,
	@FormParam("make") String make,
	@FormParam("model") String model,
	@FormParam("year") String year ){
		
		System.out.println(location);
		System.out.println(endDate);
		System.out.println(startDate);
		System.out.println(make);
		System.out.println(model );
		System.out.println(make != "undefined");
		System.out.println(model != "undefined");
		
		System.out.println(year);

		
		//TODO: remove this tempUser and replaced it with an actual user
		String user = "tempUser";
		
		CouchConnection couch = new CouchConnection("http://localhost:5984/", "gm/");
		
		
		if(!make.equals("undefined")){
			couch = new CouchConnection("http://localhost:5984/", make.toLowerCase()+"/");
		}
		if(!model.equals("undefined")){
			couch = new CouchConnection("http://localhost:5984/", model.toLowerCase()+"/");
		}
		
		
		long startDateLong = 0;
		long endDateLong = 0;
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse(endDate);
			endDateLong = d.getTime();
			d = sdf.parse(startDate);
			startDateLong = d.getTime();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(endDateLong);
		System.out.println(startDateLong);

		//System.out.println(couch.queryDB("_design/"+user));
		
		
		//String existingView = couch.queryDB("_design/"+user);
		//if(existingView != null){
		try{
			JSONObject viewDocument = new JSONObject(couch.queryDB("_design/"+user));
			couch.deleteDocuments("_design/"+user, (String)viewDocument.get("_rev"));
		}
		catch(Exception e){
		}
		
		//Checks to see if this view already exists
		// if it doesn't then make a new view
		// else if it does then just return the view
		//if(viewDocument == null){
			JSONObject viewDocument = new JSONObject();
			viewDocument.put("views", new JSONObject());
			
			JSONObject viewSentiment = new JSONObject();
			
			/*
			StringBuilder sb = new StringBuilder();
            sb.append("function(doc) {");
			sb.append("  if(doc.tweettime > 0 && doc.tweettime < 1427535144069) {");
            sb.append("    emit(null, doc.tweetsentiment);");
			sb.append("  }");
            sb.append("}");
			*/
			
			//viewSentiment.put("map", sb.toString());
			viewSentiment.put("map", "function(doc) { if(doc.tweettime > "+startDateLong+" && doc.tweettime < "+endDateLong+") { emit(null, doc.tweetsentiment); } }");
			
			/*
			sb = new StringBuilder();
            sb.append("function(keys, values, rereduce) {");
            sb.append("  if (!rereduce){");
            sb.append("    var length = values.length;");
            sb.append("    return [sum(values) / length, length];");
            sb.append("  }else{");
            sb.append("    var length = sum(values.map(function(v){return v[1]}));");
            sb.append("    var avg = sum(values.map(function(v){");
            sb.append("      return v[0] * (v[1] / length);");
            sb.append("    }));");
            sb.append("    return [avg, length];");
            sb.append("  }");
            sb.append("}");
            */	
				
			//viewSentiment.put("reduce", sb.toString());
			viewSentiment.put("reduce", "function(keys, values, rereduce) { if (!rereduce){ var length = values.length; return [sum(values) / length, length]; }else{ var length = sum(values.map(function(v){return v[1]})); var avg = sum(values.map(function(v){ return v[0] * (v[1] / length); })); return [avg, length]; } }");
			
			viewDocument.getJSONObject("views").put("sentiment", viewSentiment);
			
			
			////////////////////////////////////////////////////////
			
			
			JSONObject viewWordCount = new JSONObject();
			
			/*
			sb = new StringBuilder();
            sb.append("function(doc) {");
            sb.append("  if(doc.tweettime > 0 && doc.tweettime < 1427535144069) {");
            sb.append("    for(var key in doc){");
            sb.append("      emit(key, doc[key]);");
            sb.append("    }");
            sb.append("  }");
            sb.append("}");
			*/
			
			//viewWordCount.put("map", sb.toString());
			viewWordCount.put("map", "function(doc){  if(doc.tweettime > "+startDateLong+" && doc.tweettime < "+endDateLong+"){    for(var key in doc){      emit(key, doc[key]);    }  }}");
			
			/*
			sb = new StringBuilder();
            sb.append("function(keys, values, rereduce){");
            sb.append("  if (!rereduce){");
            sb.append("    var length = values.length;");
            sb.append("    return length;");
            sb.append("  }else{");
            sb.append("    var length = sum(values.map(function(v){return v}));");
            sb.append("    return length;");
            sb.append("  }");
            sb.append("}");
			*/
			
			//viewWordCount.put("reduce", sb.toString());
			viewWordCount.put("reduce", "function(keys, values, rereduce){  if (!rereduce){    var length = values.length;    return length;  }else{    var length = sum(values.map(function(v){return v}));    return length;  }}");
			
			viewDocument.getJSONObject("views").put("wordCount", viewWordCount);
			viewDocument.put("_id", "_design/"+user);
			viewDocument.put("language", "javascript");
			
			///////////////////////////////////////////////////////
			
			couch.createDocuments(viewDocument, false);
		//}
		//else{
			
		//}
		
		System.out.println(viewDocument.toString());
		
		JSONObject returnJSON = new JSONObject();
		
		returnJSON.put("wordCount", couch.queryDB("_design/"+user+"/_view/wordCount?group=true"));
		returnJSON.put("sentiment", couch.queryDB("_design/"+user+"/_view/sentiment"));
		
		System.out.println(returnJSON.toString());
        ReturnMessage rm = new ReturnMessage();
		rm.setResult(returnJSON.toString());
        return rm;
    }
}

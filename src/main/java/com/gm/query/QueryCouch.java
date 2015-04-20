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
    public String createView(@FormParam("endDate") String endDate,
	@FormParam("startDate") String startDate,
	@FormParam("make") String make,
	@FormParam("model") String model,
	@FormParam("year") String year ){
		
		System.out.println(endDate);
		System.out.println(startDate);
		System.out.println(make);
		System.out.println(model );
		System.out.println(make != "undefined");
		System.out.println(model != "undefined");
		
		System.out.println(year);
		//System.out.println(user);
		
		CouchConnection couch = new CouchConnection("http://localhost:5984/", "gm/");
		
		//if(user.equals("undefined")){
		//	user = "tempUser";
		//}
		if(!make.equals("undefined") || make.equals("All Makes")){
			couch = new CouchConnection("http://localhost:5984/", make.toLowerCase()+"/");
		}
		if(!model.equals("undefined") || model.equals("All Models")){
			couch = new CouchConnection("http://localhost:5984/", model.toLowerCase()+"/");
		}
		if(!year.equals("undefined") || year.equals("All Years")){
			couch = new CouchConnection("http://localhost:5984/", model.toLowerCase()+year+"/");
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

		JSONObject viewDocument = new JSONObject();
		boolean existingView = true;
		try{
			viewDocument = new JSONObject(couch.queryDB("_design/("+startDate+")("+endDate+")"));
		}
		catch(Exception e){
			existingView = false;
		}
		
		//Checks to see if this view already exists
		// if it doesn't then make a new view
		// else if it does then just return the view
		if(!existingView){
			viewDocument.put("views", new JSONObject());
			
			JSONObject viewSentiment = new JSONObject();
						
			StringBuilder sb = new StringBuilder();
			sb.append("function(doc) {");
			sb.append("  if(doc.tweettime > "+startDateLong+" && doc.tweettime < "+endDateLong+") {");
			sb.append("    emit(null, doc.tweetsentiment);");
			sb.append("  }");
			sb.append("}");		
			viewSentiment.put("map", sb.toString());	
			
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
			viewSentiment.put("reduce", sb.toString());	
			
			
			viewDocument.getJSONObject("views").put("sentiment", viewSentiment);
			
			
			////////////////////////////////////////////////////////
			
			
			JSONObject viewWordCount = new JSONObject();			
			
			sb = new StringBuilder();
			sb.append("function(doc) {");
			sb.append("  if(doc.tweettime > "+startDateLong+" && doc.tweettime < "+endDateLong+") {");
			sb.append("    for(var key in doc){");
			sb.append("      emit(key, doc[key]);");
			sb.append("    }");
			sb.append("  }");
			sb.append("}");		
			viewWordCount.put("map", sb.toString());
			
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
			viewWordCount.put("reduce", sb.toString());
			
			
			viewDocument.getJSONObject("views").put("wordCount", viewWordCount);
			
			
			viewDocument.put("_id", "_design/("+startDate+")("+endDate+")");
			viewDocument.put("language", "javascript");
			
			///////////////////////////////////////////////////////
			
			couch.createDocuments(viewDocument, false);
		}

		
		System.out.println(viewDocument.toString());
		
		JSONObject returnJSON = new JSONObject();
		
		returnJSON.put("wordCount", couch.queryDB("_design/("+startDate+")("+endDate+")"+"/_view/wordCount?group=true"));
		returnJSON.put("sentiment", couch.queryDB("_design/("+startDate+")("+endDate+")"+"/_view/sentiment"));
		
		System.out.println(returnJSON.toString());

        return returnJSON.toString();
    }	
	
}

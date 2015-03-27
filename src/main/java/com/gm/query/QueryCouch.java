
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
			
		//TODO: remove this tempUser and replaced it with an actual user
		String tempUser = "tempUser";
		
		CouchConnection couch new CouchConnection("http://localhost:5984/",	make+"/");
		
		JSONObject viewDocument = new JSONObject(couch.queryDB("_design/"+tempUser));
		if(viewDocument.has("error")){
		
		}
		else{
			JSONObject viewSentiment = new JSONObject();
			
			StringBuilder sb = new StringBuilder();
            sb.append("function(doc) {");
            sb.append("  emit(null, doc.sentiment)");
            sb.append("}");
			
			viewSentiment.put("map", sb.toString());
			
			sb = new StringBuilder();
            sb.append("function(keys, values, rereduce) {");
            sb.append("  if (!rereduce){");
            sb.append("    var length = values.length");
            sb.append("    return [sum(values) / length, length]");
            sb.append("  }else{");
            sb.append("    var length = sum(values.map(function(v){return v[1]}))");
            sb.append("    var avg = sum(values.map(function(v){");
            sb.append("      return v[0] * (v[1] / length)");
            sb.append("    }))");
            sb.append("    return [avg, length]");
            sb.append("  }");
            sb.append("}");
            	
			viewSentiment.put("reduce", sb.toString());
			
			viewDocument.getJSONObject("views").put("sentiment", viewSentiment);
			
			
			////////////////////////////////////////////////////////
			
			
			JSONObject viewWordCount = new JSONObject();
			
			sb = new StringBuilder();
            sb.append("function(doc) {");
            sb.append("  emit(null, doc.sentiment)");
            sb.append("}");
			
			viewWordCount.put("map", sb.toString());
			
			sb = new StringBuilder();
            sb.append("function(keys, values, rereduce) {");
            sb.append("  if (!rereduce){");
            sb.append("    var length = values.length");
            sb.append("    return length");
            sb.append("  }else{");
            sb.append("    var length = sum(values.map(function(v){return v}))");
            sb.append("    return length");
            sb.append("  }");
            sb.append("}");

			viewWordCount.put("reduce", sb.toString());
			
			viewDocument.getJSONObject("views").put("wordCount", view);
			
			
			///////////////////////////////////////////////////////
			
			
			couch.updateDocument("_design/tweets", viewDocument);
		}
		

        ReturnMessage rm = new ReturnMessage();
		rm.setResult("success");
        return rm;
    }


}

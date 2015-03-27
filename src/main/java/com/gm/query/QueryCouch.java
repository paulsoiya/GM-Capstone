
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

		
			
		CouchConnection couch new CouchConnection("http://localhost:5984/",	make+"/");
		
		JSONObject viewDocument = new JSONObject(couch.queryDB("_design/"));
		String viewName = "varname";
		couch.createDocuments(bulk, true);
		
		JSONObject view = new JSONObject();
		view.put("map", "function(doc) { if (doc."+viewName+" == '2015') { emit(doc._id, doc.make); } }");
		viewDocument.getJSONObject("views").put(viewName, view);
		/ouch.updateDocument("_design/tweets", viewDocument);
		
		JSONObject test = viewDocument.getJSONObject("views").put(viewName, );
		viewDocument.put("views", test);
		*/
		

        ReturnMessage rm = new ReturnMessage();
		rm.setResult("success");
        return rm;
    }


}

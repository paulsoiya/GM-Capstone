//http://docs.couchdb.org/en/latest/api/database/bulk-api.html
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import twitter4j.*;
// ^ umm ok. Needed this import for JSON objects instead of "org.json..." very weird

// import org.json.JSONArray;
// import org.json.JSONException;
// import org.json.JSONObject;


public class CouchConnection {

	private String host;
	private String database;
	
	
	//"http://localhost:5984/","gm/"
	public CouchConnection(String host, String database){
		this.host = host;
		this.database = database;
	}
	
	
	//_all_docs
	//_design/tweets/_view/buick	
	public String queryDB(String view){
		HttpURLConnection request;
		BufferedReader instream;
		String response = null;
		try{
			request = (HttpURLConnection)new URL(host+database+view).openConnection();
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
			instream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return response;
	}
	
	public JSONObject updateDocument(String id, JSONObject json) throws JSONException{
		HttpURLConnection request;
		BufferedWriter outstream = null;
		BufferedReader instream;
		JSONObject response = null;
		try{
			request = (HttpURLConnection)new URL(host+database+id).openConnection();
			request.setRequestProperty("Accept", "application/json");
			request.setRequestProperty("Content-Type", "application/json");
			request.setRequestProperty("Connection", "keep-alive");
			request.setRequestMethod("PUT");
			request.setDoOutput(true);
			request.setDoInput(true);
	        
			outstream = new BufferedWriter(new OutputStreamWriter(request.getOutputStream()));
			outstream.write(json.toString());
			outstream.flush();
			outstream.close();
	        
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			response = new JSONObject(result.toString());
			instream.close();
		}catch(IOException e){
			e.printStackTrace();
		}		
		return response;
	}
	
	public String createDocuments(JSONObject json, boolean isBulkInsert){
		HttpURLConnection request;
		BufferedWriter outstream = null;
		BufferedReader instream;
		String response = null;
		try{
			if(isBulkInsert) request = (HttpURLConnection)new URL(host+database+"_bulk_docs").openConnection();
			else request = (HttpURLConnection)new URL(host+database).openConnection();
			request.setRequestProperty("Accept", "application/json");
			request.setRequestProperty("Content-Type", "application/json");
			request.setRequestProperty("Connection", "keep-alive");
			request.setRequestMethod("POST");
			request.setDoOutput(true);
			request.setDoInput(true);
	        
			outstream = new BufferedWriter(new OutputStreamWriter(request.getOutputStream()));
			outstream.write(json.toString());
			outstream.flush();
			outstream.close();
	        
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
			instream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return response;
	}
	
	public String deleteDocuments(String id, String rev){
		HttpURLConnection request;
		BufferedReader instream;
		String response = null;
		try{
			request = (HttpURLConnection)new URL(host+database+id+"?rev="+rev).openConnection();
			request.setRequestMethod("DELETE");
        
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
			instream.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return response;
	}	
	
	

	public static void main(String[] args) {
		try {
			CouchConnection couch = new CouchConnection("http://localhost:5984/","gm/");
			JSONObject bulk = new JSONObject();
			JSONArray docs = new JSONArray();
			//System.out.println(couch.queryDB("_design/tweets/_view/buick"));
			JSONObject doc0 = new JSONObject();
			
			doc0.put("make", "Buick");
			doc0.put("year", "2015");
			/*
			for(int i = 0; i < 100000; ++i){
	        	docs.put(doc0);
	        	//couch.createDocuments(doc0, false);
	        }
			bulk.put("docs", docs);
			
			
			
			couch.createDocuments(bulk, true);
			
			*/
			
			
			//JSONObject r1 = new JSONObject();
			//couch.updateDocument("_design/tweets/_view/buick", r0);
			/*			JSONArray rows = (JSONArray)r0.get("rows");
			for(int i = 0; i < 10; ++i){
				couch.deleteDocument(rows.getJSONObject(i).getString("id"), rows.getJSONObject(i).getJSONObject("value").getString("rev"));
			}
			
			JSONObject example = new JSONObject();
	        example.put("source", "twitter");
	        example.put("make", "Buick");
	        
			JSONObject makeMany = new JSONObject();
			JSONArray docs = new JSONArray();
	        for(int i = 0; i < 100000; ++i){
	        	docs.put(example);
	        }
	        makeMany.put("docs", docs);
			couch.createDocuments(makeMany, true);
	        for(int i = 0; i < 100000; ++i){
	        	couch.createDocument(example);
	        }	
			System.out.println(makeMany.toString());
			
			JSONObject r1 = new JSONObject(couch.createDocument());
			couch.deleteDocument(r1.getString("id"), r1.getString("rev"));
*/
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
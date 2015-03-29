//http://docs.couchdb.org/en/latest/api/database/bulk-api.html

package com.gm.query;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
		BufferedReader instream = null;
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
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (instream != null)
				try {
					instream.close();
				} catch (IOException e) {}
		}
		return response;
	}
	
	public String updateDocument(String id, JSONObject json){
		HttpURLConnection request;
		BufferedWriter outstream = null;
		String response = null;
		BufferedReader instream = null;
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
	        
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			
			response = result.toString();
			System.out.println(response);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (instream != null)
				try {
					instream.close();
				} catch (IOException e) {}
			if (outstream != null)
				try {
					outstream.close();
				} catch (IOException e) {}
		}
		return response;
	}
	
	public String createDocuments(JSONObject json, boolean isBulkInsert){
		HttpURLConnection request;
		BufferedWriter outstream = null;
		BufferedReader instream = null;
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
	        
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
			System.out.println(response);
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (instream != null)
				try {
					instream.close();
				} catch (IOException e) {}
			if (outstream != null)
				try {
					outstream.close();
				} catch (IOException e) {}
		}
		return response;
	}
	
	public String deleteDocuments(String id, String rev){
		HttpURLConnection request;
		BufferedReader instream = null;
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
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (instream != null)
				try {
					instream.close();
				} catch (IOException e) {}
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
			doc0.put("varname", "2015");
			
			for(int i = 0; i < 100; ++i){
	        	docs.put(doc0);
	        	//couch.createDocuments(doc0, false);
	        }
			bulk.put("docs", docs);
			
			JSONObject viewDocument = new JSONObject(couch.queryDB("_design/tweets"));
			String viewName = "varname";
			couch.createDocuments(bulk, true);
			
			JSONObject view = new JSONObject();
			view.put("map", "function(doc) { if (doc."+viewName+" == '2015') { emit(doc._id, doc.make); } }");
			viewDocument.getJSONObject("views").put(viewName, view);
			//couch.updateDocument("_design/tweets", viewDocument);
			
			//JSONObject test = viewDocument.getJSONObject("views").put(viewName, );
			//viewDocument.put("views", test);
			
			//System.out.println(couch.updateDocument("_design/tweets", viewDocument));
			
			couch.createDocuments(bulk, true);
			
			
			
			
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

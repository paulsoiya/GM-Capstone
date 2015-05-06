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
// Looks like twitter4j already had jsons included and there was a conflict on which json class to use


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
				} catch (IOException e) {
					e.printStackTrace();
				}
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
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (instream != null)
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (outstream != null)
				try {
					outstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return response;
	}
	
	public String deleteDocuments(String id, String rev){
		return httpRequest("DELETE", host+database+id+"?rev="+rev);
	}	

	public String makeDB(String databaseName){
		return httpRequest("PUT", host+"/"+databaseName);
	}


	public String httpRequest(String verb, String url){
		HttpURLConnection request;
		BufferedReader instream = null;
		String response = null;
		try{
			request = (HttpURLConnection)new URL(url).openConnection();
			request.setRequestMethod(verb);
        
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
		}catch(IOException e){
			System.out.println("Can't delete");
		}finally{
			if (instream != null)
				try {
					instream.close();
				} catch (IOException e) {

				}
		}
		return response;
	}

	public static void main(String[] args) {

	}
	
	
	

}


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import twitter4j.*;


public class StanbolConnection {

	private String host;

	public StanbolConnection(String host){
		this.host = host;
	}
	
	public double singleSentiment(String text){
		HttpURLConnection request;
		BufferedWriter outstream = null;
		BufferedReader instream;
		String response = null;
		double sentiment = 404;
		try{
			request = (HttpURLConnection)new URL(host).openConnection();
			request.setRequestProperty("Accept", "application/json");
			request.setRequestProperty("Content-Type", "text/plain");
			request.setRequestProperty("Connection", "keep-alive");
			request.setRequestMethod("POST");
			request.setDoOutput(true);
			request.setDoInput(true);
	        
			outstream = new BufferedWriter(new OutputStreamWriter(request.getOutputStream()));
			outstream.write(text);
			outstream.flush();
			outstream.close();
	        
			instream = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = instream.readLine()) != null) {
				result.append(line);
			}
			instream.close();
			response = result.toString();
			//System.out.println(response);
			JSONObject responseJSON = new JSONObject(response);
			
			if(responseJSON.has("@graph")){
				JSONArray graphJSON = responseJSON.getJSONArray("@graph");
				
				//look for sentiment of whole document
				for( int i = 0; i < graphJSON.length(); ++i ){
					JSONObject currentSentimentObject = (JSONObject)graphJSON.get(i);
					//System.out.println(currentSentimentObject.toString());
					if( !(currentSentimentObject.get("type") instanceof String) ){
						JSONArray type = (JSONArray) currentSentimentObject.get("type");
						//check if it's the sentiment of the whole document and then take it
						if("DocumentSentiment".equals(type.getString(0))){
							sentiment = (double)currentSentimentObject.get("sentiment");
						}
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return sentiment;
	}
	


}
import twitter4j.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;

public class Twit {
	//public static final String HOST = "104.131.150.198:5984";
	//public static final String DB_NAME = "104.131.150.198:5984";
	//public static final String STANBOL = "http://swent1linux.asu.edu:8080/enhancer";
	//public static final String COUCHDB = "http://swent1linux.asu.edu:5984/";
	
	private static String STANBOL;
	private static String COUCHDB;
	private static String MYSQL;
	private static String MYSQLUSER;
	private static String MYSQLPASSWORD;
	
	public static void main(String[] args) {		
	
		Properties properties = new Properties();
		InputStream is = null;
	
  		Twitter twitter = null;
  		Query query = null;
  		QueryResult result = null;

  		Connection dbConn = null;

  		Statement statementMakes = null;
  		Statement statementModels = null;
  		Statement statementAlternateMakes = null;
  		Statement statementAlternateModels = null;
  		Statement statementModelYears = null;

  		ResultSet resultSetMakes = null;
  		ResultSet resultSetModels = null;
  		ResultSet resultSetAlternateMakes = null;
  		ResultSet resultSetAlternateModels = null;
  		ResultSet resultSetAlternateModelYears = null;
  		ResultSet resultSetModelYears = null;

  		try { 
			is = new FileInputStream("build.properties");
			properties.load(is);
			
			STANBOL = properties.getProperty("stanbol");
			COUCHDB = properties.getProperty("couchdb");
			MYSQL = properties.getProperty("mysql");
			MYSQLUSER = properties.getProperty("mysql-user");
			MYSQLPASSWORD = properties.getProperty("mysql-password");
			
			System.out.println(STANBOL);
			System.out.println(COUCHDB);
			System.out.println(MYSQL);
			System.out.println(MYSQLUSER);
			System.out.println(MYSQLPASSWORD);
			
   			Class.forName("com.mysql.jdbc.Driver").newInstance();

			dbConn = DriverManager.getConnection(MYSQL, MYSQLUSER, MYSQLPASSWORD);
   			//dbConn = DriverManager.getConnection("jdbc:mysql://localhost/testGM", "root", "");
   			//dbConn = DriverManager.getConnection("jdbc:mysql://localhost/testGM", "root", "digiocean2@");

			
   			statementMakes = dbConn.createStatement();
   			resultSetMakes = statementMakes.executeQuery("SELECT * FROM makes");

   			statementModels = dbConn.createStatement();
   			resultSetModels = statementModels.executeQuery("SELECT * FROM models");

   			statementModelYears = dbConn.createStatement();
   			resultSetModelYears = statementModelYears.executeQuery("SELECT * FROM model_years");

   			statementAlternateMakes = dbConn.createStatement();
   			resultSetAlternateMakes = statementAlternateMakes.executeQuery("SELECT * FROM make_alternates");

   			statementAlternateModels = dbConn.createStatement();
   			resultSetAlternateModels = statementAlternateModels.executeQuery("SELECT * FROM model_alternates");
  		}
  		catch(Exception e) {
  			e.printStackTrace();
  		}
		finally {
			if(is != null){
				try {
					is.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
 
  		List<Status> tweets = new ArrayList<Status>();

		try {
			//Couch connection
			CouchConnection cc = new CouchConnection(COUCHDB, null); 
		    // Filter
		    FilterQuery filter = new FilterQuery();
		    ArrayList<String> paramsAsList = new ArrayList<String>();

		    // Add makes to filter list
		    ArrayList<String> makes = new ArrayList<String>();
		    while(resultSetMakes.next()) {
				makes.add("#"+resultSetMakes.getString("make_name"));
				cc.makeDB(resultSetMakes.getString("make_name"));
		    } 

		    // Add models to filter list
		    ArrayList<String> models = new ArrayList<String>();
		    ArrayList<Integer> modelIds = new ArrayList<Integer>();
			while(resultSetModels.next()) {
				models.add("#"+resultSetModels.getString("model_name"));
				cc.makeDB(resultSetModels.getString("model_name"));
				modelIds.add(resultSetModels.getInt("model_id"));
		    } 

 			ArrayList<String> modelYears = new ArrayList<String>();
 			ArrayList<Integer> modelYearMakeIds = new ArrayList<Integer>();
			while(resultSetModels.next()) {
				modelYears.add(resultSetModelYears.getString("model_year"));
				modelYearMakeIds.add(resultSetModelYears.getInt("model_id"));
		    } 

		    //Create databases on couch if doesn't already exist
		    for(int i = 0; i < modelYears.size(); ++i){
		    	for(int j = 0; j < modelIds.size(); ++j){
		    		if(modelIds.get(i) == modelYearMakeIds.get(i)){
		    			cc.makeDB((models.get(i)+modelYears.get(i)).toLowerCase());
		    		}
		    	}
		    }


		    // Add altenates to filter list
		    ArrayList<String> alternateMakes = new ArrayList<String>();
			while(resultSetAlternateMakes.next()) {
			    alternateMakes.add("#"+resultSetAlternateMakes.getString("make_alternate_name"));
		    }

		    // Add altenates to filter list
		    ArrayList<String> alternateModels = new ArrayList<String>();
			while(resultSetAlternateModels.next()) {
			    alternateModels.add("#"+resultSetAlternateModels.getString("model_alternate_name"));
		    }

		    for(String make : makes) {
		    	paramsAsList.add(make);
		    }

		    for(String model : models) {
		    	paramsAsList.add(model);
				paramsAsList.add(model+"2014");
				paramsAsList.add(model+"2015");
		    }

		    for(String alternate : alternateMakes) {
		    	paramsAsList.add(alternate);
		    }

		    for(String alternate : alternateModels) {
		    	paramsAsList.add(alternate);
		    }
		    
		    final String[] filterTags = paramsAsList.toArray(new String[paramsAsList.size()]);
		    for(String tag : filterTags) {
		    	System.out.println(tag);
		    }
			
			
			
			TwitterStream stream = new TwitterStreamFactory().getInstance();
		    StatusListener listener = new StatusListener() {
		    	int count = 0;
		    	
		    	public void onStatus(Status status) {

		    		System.out.println("#: " + ++count + " @" + status.getUser().getScreenName() + ": " + status.getText());
		    		// NEED TO PARSE STUFF HERE
		    		// DONT KEEP IF:		
					String regexAtUser = "@\\w+"; // removes start with "@"
					String regexBreakLines = "[(\\r)(\\n)]"; // removes break lines in the tweet
					String regexLink = "(\\S+\\.(\\w+)(\\/\\S+)?)"; // removes all links
					String regexRemaining = "[^a-zA-Z.,!\\s]"; // removes all non-letter characters
					boolean ignoreTweet = false;
					
					String text = status.getText();
					
					Pattern p = Pattern.compile(regexAtUser);
					Matcher m = p.matcher(text); 
					text = m.replaceAll("");
					p = Pattern.compile(regexBreakLines);
					m = p.matcher(text); 
					text = m.replaceAll("");
					p = Pattern.compile(regexLink);
					if( !text.equals(m.replaceAll("")) ) ignoreTweet = true;
					m = p.matcher(text); 
					text = m.replaceAll("");
					p = Pattern.compile(regexRemaining);
					m = p.matcher(text); 
					text = m.replaceAll("");
					
					
					//Puts all the words in the tweet into a hashmap
					String lowerCaseText = text.toLowerCase();
					String[] textArray = lowerCaseText.split(" ");
					Map<String, Integer> wordCount = new HashMap<>();
					for (String word: textArray) {
						if(!word.equals("")){
							if (wordCount.containsKey(word)) {
								wordCount.put(word, wordCount.get(word) + 1);
							} else {
								wordCount.put(word, 1);
							}
						}
					}
					
					
					
		    		JSONObject injectObj = new JSONObject();

		    		boolean hasLocation = false;
		    		String location = "";

		    		if(status.getGeoLocation() != null)
		    		{
		    			location = status.getGeoLocation().toString();
		    			hasLocation = true;
		    		}
		    		String time = status.getCreatedAt().toString();
					//TODO change this to round to the day
					Long timeLong = status.getCreatedAt().getTime();
		    		String numFaves = String.valueOf(status.getFavoriteCount());
		    		String numRetweets = String.valueOf(status.getRetweetCount());
		    		String length = String.valueOf(status.getText().length());
					String language = String.valueOf(status.getLang());
					StanbolConnection sc = new StanbolConnection(STANBOL);
					System.out.println(text);
					double sentiment = sc.singleSentiment(text);
					
					if(sentiment == 404) ignoreTweet = true;
					
					if("en".equals(language) && !ignoreTweet){
						try {
							
						
							//injectObj.put("source", String.valueOf(status.getSource()));
							injectObj.put("tweettext", text);
							if(hasLocation) {
								injectObj.put("tweetlocation", location);
							}
							injectObj.put("tweettime", timeLong);
							//injectObj.put("numFaves", numFaves);
							//injectObj.put("numRetweets", numRetweets);
							//injectObj.put("length", length);
							
							
							for (Map.Entry<String, Integer> entry: wordCount.entrySet()) { 
								injectObj.put(entry.getKey(), entry.getValue());	
							}
							
							
							
							
							injectObj.put("tweetsentiment", sentiment);

							CouchConnection cc = new CouchConnection(COUCHDB, "gm/");	
							cc.createDocuments(injectObj, false);    
							
							for(String tag : filterTags) {
								System.out.println();


								if(text.toLowerCase().contains(tag.toLowerCase().substring(1))){;
									for(int i = 0; i < 100; ++i){
										System.out.println("");
										System.out.println(tag.toLowerCase());
										System.out.println("");
									}
									CouchConnection couch = new CouchConnection(COUCHDB, tag.toLowerCase().substring(1)+"/");	
									couch.createDocuments(injectObj, false);    
								}
							}
							
							count++;
							
						}
						catch(JSONException je) {
							je.printStackTrace();
						}
					}
					
					
		    	}

		    	public void onStallWarning(StallWarning warning) {
		    		System.out.println("StallWarning: " + warning);
		    	}

		    	public void onScrubGeo(long userId, long upToStatusId) {
	                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	            }

	            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
	                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	            }

	            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
	                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	            }

	            public void onException(Exception ex) {
	                ex.printStackTrace();
	            }
		    };
		    //listener.filter("#chevy OR #generalmotors OR #gmc OR #buick");
		    stream.addListener(listener);

			
		    filter.track(filterTags);
		    stream.filter(filter);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Gathered: " + tweets.size() + " tweets..");
			System.exit(0);
		}
		
	}
}

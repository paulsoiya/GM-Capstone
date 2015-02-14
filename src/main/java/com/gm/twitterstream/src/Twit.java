import twitter4j.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.sql.*;

public class Twit {
	public static void main(String[] args) {		
  		Twitter twitter = null;
  		Query query = null;
  		QueryResult result = null;

  		Connection dbConn = null;

  		Statement statementMakes = null;
  		Statement statementModels = null;
  		Statement statementAlternates = null;

  		ResultSet resultSetMakes = null;
  		ResultSet resultSetModels = null;
  		ResultSet resultSetAlternates = null;

  		int iterationCount = 0;
  		final int ITERATION_CAP = 5000;

  		public static final String HOST = "104.131.150.198:5984";
  		public static final String DB_NAME = "104.131.150.198:5984";

  		
  		try { 
   			Class.forName("com.mysql.jdbc.Driver").newInstance();
   			dbConn = DriverManager.getConnection("jdbc:mysql://localhost/testGM", "root", "digiocean2@");
   			
   			statementMakes = dbConn.createStatement();
   			resultSetMakes = statementMakes.executeQuery("SELECT * FROM filterquerymakes WHERE filterid = 0");
   			resultSetMakes.next();

   			statementModels = dbConn.createStatement();
   			resultSetModels = statementModels.executeQuery("SELECT * FROM filterquerymodels");
   			resultSetModels.next();

   			statementAlternates = dbConn.createStatement();
   			resultSetAlternates = statementAlternates.executeQuery("SELECT * FROM filterqueryalternates");
			resultSetAlternates.next();	
  		}
  		catch(Exception e) {
  			e.printStackTrace();
  		}
 
  		List<Status> tweets = new ArrayList<Status>();

		try {
			// MongoClient mongoClient = new MongoClient("localhost", 27017);
		    TwitterStream stream = new TwitterStreamFactory().getInstance();
		    StatusListener listener = new StatusListener() {
		    	int count = 0;
		    	
		    	public void onStatus(Status status) {
		    		// System.out.println("#: " + ++count + " @" + status.getUser().getScreenName() + ": " +
		    		// 	status.getText());
		    		// NNED TO PARSE STUFF HERE
		    		// DONT KEEP IF:
		    		// beings with "http://" or "www"

		    		// removes start with "@"
		    		// remove all non-letter characters
		    		JSONObject injectObj = new JSONObject();

		    		boolean hasLocation = false;
		    		String location = "";

		    		if(status.getGeoLocation() != null)
		    		{
		    			location = status.getGeoLocation().toString();
		    			hasLocation = true;
		    		}
		    		String time = status.getCreatedAt().toString();
		    		String numFaves = String.valueOf(status.getFavoriteCount());
		    		String numRetweets = String.valueOf(status.getRetweetCount());
		    		String length = String.valueOf(status.getText().length());

		    		String text = status.getText();

		    		try {
		    			injectObj.put("text", text);
		    			if(hasLocation) {
		    				injectObj.put("location", location);
		    			}
			    		injectObj.put("time", time);
			    		injectObj.put("numFaves", numFaves);
			    		injectObj.put("numRetweets", numRetweets);
			    		injectObj.put("length", length);
		    		}
		    		catch(JSONException je) {
		    			je.printStackTrace();
		    		}
		    		

		    		System.out.print("OBJECT: " + injectObj.toString());
		    		CouchConnection cc = new CouchConnection(HOST, 
		    		cc.

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

			// Filter
		    FilterQuery filter = new FilterQuery();
		    ArrayList<String> paramsAsList = new ArrayList<String>();
		    String[] makes = resultSetMakes.getString(1).split(",");
		    String[] models = resultSetModels.getString(1).split(",");
		    String[] alternates = resultSetAlternates.getString(1).split(",");

		    for(String make : makes) {
		    	paramsAsList.add(make);
		    }

		    for(String model : models) {
		    	paramsAsList.add(model);
		    }
		    for(String alternate : alternates) {
		    	paramsAsList.add(alternate);
		    }
		    
		    String[] filterTags = paramsAsList.toArray(new String[paramsAsList.size()]);
		    for(String tag : filterTags) {
		    	System.out.println(tag);
		    }
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

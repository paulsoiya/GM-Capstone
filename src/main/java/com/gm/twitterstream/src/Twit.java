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

  		Statement statement = null;
  		ResultSet resultSet = null;
  		
  		try { 
   			Class.forName("com.mysql.jdbc.Driver").newInstance();
   			dbConn = DriverManager.getConnection("jdbc:mysql://localhost/testGM", "root", "cst316");
   			statement = dbConn.createStatement();
   			resultSet = statement.executeQuery("SELECT * FROM filterquery WHERE filterid = 0");
   			
   			resultSet.next();
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
		    		System.out.println("#: " + ++count + " @" + status.getUser().getScreenName() + ": " +
		    			status.getText());
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
		    String[] makes = resultSet.getString(2).split(",");
		    String[] models = resultSet.getString(3).split(",");
		    String[] alternates = resultSet.getString(5).split(",");

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
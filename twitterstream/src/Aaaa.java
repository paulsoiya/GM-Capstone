import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.io.InputStream;
import java.util.Enumeration; 
import java.io.FileInputStream;

public class Aaaa {
  public static void main(String[] args) {
 
	write();
	
	printThemAll();
  }
  
  public static void write(){
	Properties prop = new Properties();
	OutputStream output = null;
 
	try {
 
		output = new FileOutputStream("config.properties");
 
		// set the properties value
		prop.setProperty("stanbol", "http://swent1linux.asu.edu:8080/enhancer");
		prop.setProperty("couch", "http://localhost:5984/");
		prop.setProperty("mysql", "jdbc:mysql://localhost/testGM");
		prop.setProperty("mysql-password", "");
		prop.setProperty("mysql-user", "root");
 
		// save properties to project root folder
		prop.store(output, null);
 
	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
	}
  }
  public static void printThemAll() {
 
	Properties prop = new Properties();
	InputStream input = null;
 
	try {
 
		String filename = "config.properties";
		input = new FileInputStream(filename);
		if (input == null) {
			System.out.println("Sorry, unable to find " + filename);
			return;
		}
 
		prop.load(input);
 
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = prop.getProperty(key);
			System.out.println("Key : " + key + ", Value : " + value);
		}
 
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
  }
}
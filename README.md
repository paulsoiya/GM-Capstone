##GM Capstone README

This is the README for the ASU / GM eProject, also known as a Capstone. 

### Project Structure:

The Web Page files will reside under src/main/webapp

The Java files will reside under src/main/java/com/gm (the com/gm is a java standard package naming convention) 

GM-Capstone/  
├── src/  
│...├── main/  
│......├── java/  
│......│...├── com/  
│......│......├── gm/ (The java files will be here in separate packages)  
│......├── webapp (HTML/CSS/Javascript files will be here)  

│......├── resources  
│.........├── META-INF (Contains the persistence.xml file used for defining persistence   units)  
│.........├── SQL (Contains the database model and SQL files)  

### Development Setup 

#### Dependencies
 - [Maven 3.2.5] (http://maven.apache.org/download.cgi)
 - [MySQL Community Server 5.6] (http://dev.mysql.com/downloads/mysql/) 
 - [JDK 1.6 ] (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
 - [Ant 1.9.4] (http://ant.apache.org/bindownload.cgi)

#### Build entire project

1. Uses "mvn" to build, simply use the following command in the root directory:
 - mvn clean install
2. TODO: what else we need to do

#### Java application to stream Twitter

├── gm/  
│...├── twitterstream/  
│......├── lib/  
│......├── src/  
│......├── build.xml  
│......├── dbSetub.txt  

 1. Database setup script must have been run on machine with a MySQL install. In MySQL Workbench: 
  - File > New Query Tab
  - Type "CREATE DATABASE dbname;"                    
  -- # NOTE: Currently, dbname = testGM
  - Query > execute                                   
  -- # OR, click the lightning bolt
  - File > Open SQL Script... > dbSetup.txt
  - Query > execute                                   # OR, click the lightning bolt
 2. Ensure your SQL root password is correct:
  - TODO: extract db credentials to properties file
  - For now: In Twit.java, ~ line 38, change dbConn root password to your password
 3. Uses "ant" to build, simply use the following commands in the twitterstream directory:  
  - ant compile  
  - ant execute

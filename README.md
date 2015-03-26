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
  - Query > execute
  -- # OR, click the lightning bolt
 2. Ensure your SQL root password is correct:
  - TODO: extract db credentials to properties file
  - For now: In Twit.java, ~ line 38, change dbConn root password to your password
 3. Uses "ant" to build, simply use the following commands in the twitterstream directory:  
  - ant compile  
  - ant execute
  
  
  
#### Apache Stanbol Installation  
1.  
 - export MAVEN_OPTS="-Xmx1024M -XX:MaxPermSize=256M"
 - svn co http://svn.apache.org/repos/asf/stanbol/trunk stanbol
 - cd stanbol
 - mvn clean install
 - cd launchers
 - java -Xmx1g -jar stable/target/org.apache.stanbol.launchers.stable-1.0.0-SNAPSHOT.jar

2. In a new terminal, go to the following directory in your stanbol directory:
 - cd stanbol/data/sentiment/sentiwordnet
 - mvn install -DskipTests -PinstallBundle -Dsling=http://localhost:8080/system/console
 -- This needs to be done each time the instance of stanbol is launched.

3. Add sentiment-wordclassifier and sentiment-summarization to the engines in the default chain
 - Go here: http://localhost:8080/enhancer/chain
 - Click the configure link next to default, default credentials admin/admin
 - Set the engine chain to this order:
 -	langdetect
 - 	opennlp-sentence
 -	opennlp-token
 -	opennlp-pos
 -	opennlp-ner
 -	sentiment-wordclassifier
 -	sentiment-summarization

4. Stanbol's endpoint example
 - curl -X POST -H "Accept:  application/json" -H "Content-type: text/plain" --data "This is a positive sentence because I like sentences. This is a negative sentence because I hate sentences.  I like animals.  I hate hats." http://localhost:8080/enhancer




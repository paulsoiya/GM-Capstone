GM Capstone ReadMe:


Project Structure:

The Web Page files will reside under src/main/webapp

The Java files will reside under src/main/java/com/gm (the com/gm is a java standard package naming convention) 



GM-Capstone/
├── src/
│   ├── main/
│   	├── java/
│   	│	├── com/
│   	│		├── gm/ (The java files will be here in separate packages)
│ 	├── webapp (HTML/CSS/Javascript files will be here)

|   ├── resources
|   	├── META-INF (Contains the persistence.xml file used for defining persistence units)
|   	├── SQL (Contains the database model and SQL files)

Java application to stream Twitter - Package: twitterstream
1) database setup script must have been run on machine with a mysql install. Currently, dbname = testGM
2) Uses "ant" to build, simply use the following commands in the root directory:
	ant compile
	ant execute
3) TODO: extract db credentials to properties file


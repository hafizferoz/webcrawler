#Webcrawler Project
This project consumes a list of web urls and crawls the list for a search string.

To run the application:
build the spring boot project using maven
mvn clean install -DskipTest=true

To run the application:
run the executable jar using java -jar or WebcrawlerApplication class

To test the application:
use post rest request at below path

	http://localhost:8080/webcrawler/rest/search
	
	
	{
    "urls": [
        "https://www.google.com/",
        "https://www.yahoo.com/"
    ],
    "breakPoint": 500,
    "searchStr": "search"
	}
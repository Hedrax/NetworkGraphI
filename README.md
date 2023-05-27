# NetworkGraphI
A Network Analysis project to analyse a constructed graph based on collected twitter accounts data. 
The main aim to the project was to inspect the connections between the people and discover community within a bigger community in a trial to seek knowledge of 
the hidden connections between the people.\
The second aim of the project was to analyse the algorithms used in the Main goal.\
***The program follows the Command design pattern for the sake of ease of use.***
## Main Componants
* Scraping Tool
* Temperary Graph Constructor (GraphControl)
* SQLite Protable Database
* Network Analysis
## Built with
* [Java 18](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html): object-oriented language that was made to be simple to read, write, and learn
* [Selenium](https://www.selenium.dev/): An open-source, automated testing tool used to test web applications across various browsers
* [Chrome Driver](https://chromedriver.chromium.org/): WebDriver is an open source tool for automated testing of webapps across many browsers.
It provides capabilities for navigating to web pages, user input, JavaScript execution, and more.
* [JDBC Driver](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16): JDBC™ (Java Database Connectivity) API developed by Sun Microsystems, 
now part of Oracle, that provides a standard way to access data using the Java™ programming language
* [SQlite](https://www.sqlite.org/index.html): Lightweight and compact database that does not require any kind of server to run.
* [JUNG](https://www.sqlite.org/index.html): Java Universal Network/Graph Framework--is a software library that provides a common and extendible language for the modeling,
analysis, and visualization of data that can be represented as a graph or network.
## Usage Instructions
The project archtecture follows the command Design pattern for the ease of use and excution.\
To Run the features that the program provides input \
`Command>> help`
### Using Scapping Tool
Set Twitter Accounts Email and password and the number of accounts you did put in the program in the Specified Fields of email and username in the Profile.java\
    
    String password = "";
    
    public static int numberOfProfiles = 10;
    
    case 0:
          this.username = "";
          this.userDataPath = "cache\\userData#1";
          break;
      case 1:
          this.username = "";
          this.userDataPath = "cache\\userData#2";
          break;
      case 2:
          this.username = "";
          this.userDataPath = "cache\\userData#3";
          break;
      case 3:
          this.username = "";
          this.userDataPath = "cache\\userData#4";
          break;
      case 4:
          this.username = "";
          this.userDataPath = "cache\\userData#5";
          break;
      case 5:
          this.username = "";
          this.userDataPath = "cache\\userData#6";
          break;
      case 6:
          this.username = "";
          this.userDataPath = "cache\\userData#7";
          break;
      case 7:
          this.username = "";
          this.userDataPath = "cache\\userData#8";
          break;
      case 8:
          this.username = "";
          this.userDataPath = "cache\\userData#9";
          break;
      case 9:
          this.username = "";
          this.userDataPath = "cache\\userData#10";

You may also wanted to create dir cache\userData#i as *i* from 1 to 10\
After that use the Command of login to automaticlly login in all available profiles of the chrome driver

       Command:>> login
If you starting to run the project for the first time you also may wanted to inject an initail node to start scrapping the data from\
If assumed that the username is user1111 put the command
       
       Command:>> db-inj
       Enter username: user1111
       
Initialize The Scrapping by

      Command:>> getNodes
      
## Clearing Resbonsiblity
* We are not responsible of any misusing of the tool we created
* We're not publishing any data that we used in our research
* Any Data we obtained we only used for academia perpose
## Licence
This project is open sourced under MIT license.

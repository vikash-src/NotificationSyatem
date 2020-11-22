# NotificationSystem

It is a centralized generic service for notifications that can be used by a variety applications for their notification needs. It accepts messages in a JSON String format. [Sample message can be seen here](#sample-message). Currently it has only Email processor but the design it capable of easily adding various types of messages.

# Requirements
  * Java 1.8 or newer
  * Kafka
  
# Getting Started
## Building
After cloning the repository, building is just a matter of running a standard Gradle build:
     
       gradle clean build
       
Jar will be generated in lib folder in root directory.

## Running the Jar
### Unix Environment
run start.sh from terminal

      bash -x start.sh
      
### Windows Environment
you can run NotificationSystem.jar directly from root directory using command line

      java -jar lib\NotificationSystem.jar
      
**Note**
This project runs on default port 8080. This can be overridden by adding following config to `start.sh` or command line: 

      --server.port=<port>
      
**Note**
Custom logging can be configured for this project using logback.xml. For configuring logback.xml add following config to `start.sh` or command line: 

      -Dlogging.config=<path to logback.xml>
      
## Configuration
This project has 2 configuration files with default values.
 * **[application.yaml](src/main/resources/application.yaml)**
 * **[smtpConfig.properties](src/main/java/resources/smtpConfig.properties)**

**Note** All the properties can be customized. 
For customizing you can create new files and customized values. once you create the custom file it can be used by adding following config to `start.sh` or command line: 
       
       -Dspring.config.location=file:<file-path1>,file:<file-path2>


 ## Sample Message
    
    {
    "from":"vikash@gmail.com",
    "to":["to.@gmail.com"],
    "cc":["cc.@gmail.com"],
    "bcc":["bcc.@gmail.com"],
    "subject":"Subject",
    "body":"This is body",
    "smtpProfileId":null,
    "notificationType":"EMAIL"
    }
    

# NotificationSystem

It is a centralized generic service for notification that can be used by a variety consuming application for their notification needs. It accepts messages in a generic format. currently it has only Email processor but the design it capable of easily adding various types of messages.

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

      java -jar lib\Notification.jar
      
**Note**
This project runs on default port 8080. This can be overridden by adding following config to `start.sh` or command line: 

      --server.port=<port>
      
**Note**
Custom logging can be configured for this project using logback.xml. For configuring logback.xml add following config to `start.sh` or command line: 

      -Dlogging.config=<path to logback.xml>
      
## Configuration

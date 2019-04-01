# Introduction
Carfax Coding Challenge

# Software needed
- [JDK 1.8](https://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
- [Apache Maven](http://maven.apache.org)
- [Docker](http://docker.com)
- [curl](https://curl.haxx.se)

# Building the Docker Image
Run the following maven command:

   **mvn clean package docker:build**

# Running the service
Run the following docker command:
 
   **docker run -p 8080:8080 -t carfax/challenge**

# Test the service
Run the following curl command:

   **curl http://localhost:8080/vehicle-records/VSSZZZ6JZ9R056308/odometer-rollback**
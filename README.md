
# zooplus-task

**Design

I have used Spring boot wirh Thymeleaf to build this application. The application users third party API to fetch the Location and Cryptocurrency details.

**Tooling

Java IntelliJ IDE. Maven to build this project. Lombok to reduce boiler plate code.

**Running application

1. Clone the code to your local system, open terminal(command) window and go to sellics-task folder.

2. Build project with Maven

    `mvn clean install`

3. On success completion

   `java -jar target/assignment-0.0.1-SNAPSHOT.jar`

Open this url in browser to load the applicaiton

http://localhost:8080/form

** Assumptions:

1. Application is fetches the price of Top 10 cryptocurrency (Hardcoded)
2. Pricing information is fetched from Coin API (can't guarantee the market price)
3. Location information is fetched from IP Geolocation service (Accepts only IPv4 addresses)
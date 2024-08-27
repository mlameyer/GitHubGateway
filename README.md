# GitHubGateway

## Overview
Starting from a high level I chose to treat this api as a gateway api. I am imagining there is a larger system or service that is internal to the comapny and we want to have an individual api that is responsible for the direct interaction with the third party service. In this case, Github. The gateway allows us to separate any complexity we need to deal with when interacting with the third party's api. An Example might be security. We don't want our internal service exposed outside of our firewall and other protections. So implementing a gatway api can limit exposure.

## Organization
This api is fairly lightweight and simple. I chose a layed architecture approach. I have my controller which deals with the incoming requests and responses back to the client and then I have a repository layer that is soley responsible for the direct interaction with the third party service. Internally in the repository project I also implemented a mapping class so that I can map between the client dto class (pojo) and the internal dto classes. The reason for my choice in this approach is that the internal classes more closely resemble the json data structure being returned from github/users and github/users/{user}/repos. By creating an internal class I limit the exposure and keep the client dto clean from any unwanted variables and logic. I then rely on the internal mapper to map between the internal dto to the client dto. Layering the api in this manner allowed me to separate areas of concern which reduced class sizes and kept logic sparate, small, and more easy to test. I implemented interfaces and dependency injection so that my various classes would not be dependent on concrete implementations and also made testing easier.

## Improvements
There are a few areas for improvement.
1. I would make the calls to github asynchronous for overall better concurrency.
2. I would add a validation piece to the controller layer so that I can validate the input the client submits is valid or return a proper error such as a 422 Unprocessable Entity or 400 Bad Request
3. I would build out a proper error handler for the various potential status codes returned from github to better handle different situations. For now I opted to just handle 404 not found and cover everything else under a 500 Internal server error.
4. I would add caching to prevent the client from spaming gitub with the same request and improve performance of the service
5. I would add authorization and authentication to the api. Since this is an api that I am exposing outside of the company's firewall. It would be best practice.
6. I would add additional unit tests to cover my mapper and my pojos. I believe that all code should have coverage as long as it is reasonable. Example when not to is if there is one or two lines of code that would take a sizable amount of time and complexity to mock or setup test data around it even though the rest of the code is covered. I would look at it more holistically. Such as am I covering all the potential use cases within the class and not just one or two lines contained in the class.

## How To build and run
1. From intellij ide or eclipse
   - run from the ide src/main/java/github/gateway/gateway/GatewayApplication.java
2. Build using maven and run jar file on local machine
   - run mvn clean
   - run mvn compile
   - run mvn package
   - navigate to local folder where maven creates the jar file
   - execute using cmd java -jar gateway-0.0.1-SNAPSHOT.jar
3. Use either postman or open a webrowser of choice and paste in the url http://localhost:8080/users/octocat Should get back payload and 200 response
4. Try using your own github username Should get back payload and 200 response
5. Try using http://localhost:8080/users/qewrter Should get back no payload and a 404 response

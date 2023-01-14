# movie-rama
For this project the technologies and libraries that I have used are:
Java 11, Maven 3.6.2, SpringBoot, SpringData, SpringSecurity, H2 embeded database, MapStruct, Junit, Mockito, Lombok


- Clarification
  In the Controllers I have used @Controller so I could use Model, to communicate with the UI. I a production ready application, where BE is communicating with a client Side application, we would use  @RestController  and the endPoints would return ResponseEntity for a Json response. 


To build this project, you need to have installed in your PC Java 11 and Maven 3.6.2.
After downloading the sourceCode, you can use an IDE like IntelliJ and through the terminal (in the project root) :
- run command ->  mvn clean install  or mvn clean install -DskipTests
During this process, project will download all the necessery dependencies. Please be sure, that your IDE is supporting Lombok.
After the succeess of the build, you can run the application by : 
- using command  -> mvn spring-boot:run (in the project roo).

Because we use SpringBoot and H2 database, Application has an embeded tomcat and DB. 
This means that the application will run without any issue.
to have access in the DB while the application is running, using a browser please go to -> localhost:port/h2-console   No password is needed.
In the property file, the port has been configured as 8081. (you can change it by going at application.properties)
Also at the application.properties we are keeping the configuration for H2 embeded DataBase. 

How to create a running Jar.
After building project, in the terminal and in the project root you need to run command
mvn package 
The jar will be created inside the target directory. 
To run this jar file, through the command line, navigate to the directory where the jar file exists. 
Run command -> java -jar (file_name).jar

![](https://github.com/SoloJan/bookstore/workflows/tests/badge.svg)

# bookstore
This is an example project to demonstrate the tech stack I use and like.

It is a very simple bookstore application. A shopkeeper can order books for his store and a customer can buy the books, but only if they are in stock. There is a very limited set of books available and there is only one store. Both the shopkeeper and the customer can see the list of shops, and books. There is a user with username and password customer, and a user with username and password. The easiest way to interact with the application is to use the swagger documentation. Once the software runs localy you can visit it at http://localhost:8080/swagger-ui/index.html you need to login with a username developer and password developer. From there you can interact with all the api's, there is no frontend yet at this moment.   

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#general-project-setup">General project setup</a>
      <ul>
        <li><a href="#the-model-layer">The model layer</a></li>
        <li><a href="#the-service-layer">The service layer</a></li>
        <li><a href="#the-api-layer">The api layer</a></li>
      </ul>
    </li>
    <li>
      <a href="#database-setup-and-management">Database setup and management</a>
      <ul>
        <li><a href="#h2-database">H2-Database</a></li>
        <li><a href="#postgres-database">Postgres-Database</a></li>
        <li><a href="#flyway">Flyway</a></li>
        <li><a href="#hibernate-and-jpa">Hibernate and JPA</a></li>
      </ul>
    </li>
    <li>
      <a href="#testing">Data</a>
      <ul>
        <li><a href="#itegration-test-with-rest-assured">Itegration test with rest assured</a></li>
        <li><a href="#good-old-unit-test">Good old unit test</a></li>
        <li><a href="#good-old-unit-test">Service testing</a></li>
      </ul>
    </li>
  </ol>
</details>



# general project setup
This is a spring boot project, using maven as a build tool. My project is structured by type it has different packages for different types of components, so one package for the service layer, one for the model layer and one for the rest api layer. The alternative is to structure a project by domain types so putting everything which belongs to books in one folder and everything which has to do with bookstores in an other. The later approach is much more domain driven and focussed on functionality and thats why I like that more as an concept. But structuring the project by domain makes is harder to maintain, very often the domain logic changes and very ofteren there are links between the different domain objects which often creates spagethi code so thats why I use the more technical focus. I'll go in to more detail in the different parts of my structure

## The model layer
The model defines the core of the project. It defines the objects and relations between the objects in this world. And it contains logic to make sure the model stays consistent. For example in this project a bookstore can have different books in stock and it keeps track of the stock count, the model logic makes sure that it can never have a negative amount of books in store. I use JPA and Hibernate as an orm which maps the model to a database model, however this is not the core purpose of the model the model also works perfectly on its own without a database as you can see in the unit test. 

## The service layer
The service layer defines different ways in which you can interact with the software. In this project it mainly interacts with the model but the service layer could also do different tasks like sending emails, or interacting with an external rest interface. I like to have as many services as possibles all with a dedicated task. A service can and should use other service. The services I have here get and store data in a database using a repository I general considder it a code smell if a service has multiple repositories, dealing with a different repository should in general go to a dedicated service. I also do most of my exception handling and logging in the service layer. The service also contains all the logic for interacting with the model for example wheter a user buys a book, or a shopkeeper throwing away a book is the same for the model because the book leaves the store, but its not the same for the user, the customer expects an exception when the book isn't there but that does not matter for the shopkeeper who is not intrested in the actual book but just wants it gone.  

## The api layer
The api layer defines how external users can interact with the software. It should therefore be stable and well documented. The api layer should only contain logic which has to do with translating the code to a rest interface. In general you should be able to add a soap interface to the project without reusing anything from the api layer and without touchign anything in the service layer. The component methods should al have one or two lines of code and not more, the only thing they need to do is call a dedicated service map the output and return it. The dto's are the model which is represented to the outside world, having seperate entities for this instead of the core model makes it possible to change the core model without changing the interface to the outside world it also makes sure that internal properties like the database id's are not mapped to the outside world. The api layer might seems very simple but a lot of more complex things are done by spring, changing an annotation or spring property 
can change the behavior dramaticly. Using the right properties and annotations is therefore part of the program logic and should be tested as well.

# Database setup and management

Spring allows you to have different profiles for different system configurations. It makes it possible to run the program with different databases. You can start a  profile eihter by specifying a parameter in you run configurations or when you start it from the commandline, or you can change the default profile in the application.properties which is spring.profiles.default=H2. The databases connection details are in the profiles if you want to use a different jdbc url or a different user 

## H2 database 
H2 is an in memory database, its configuration options are set in the application-H2.properties. The thing I like about the H2 database, is that you dont have to setup anything to make it work. This makes it great for example applications and also for testing. The database is created when the program starts and disapears when the program stops, so you can play arround and start with a clean database everytime. You could also configure it to realy store the data but it is not realy meant as a production database. You can visist the H2 console on http://localhost:8080/h2-console/ login with developer, developer to see the console. Next you need to configure the jdbc url jdbc:h2:mem:bookstoredb and user and pasword flyway you can than see and modify the data in your webbrowser. But you can also use the same credentials to visit the database with any other database viewer. By default the application starts with the h2 database and it is also used in test.

## Postgres database 
There is also a postgres profile its configuration options are set in the application-postgres.properties. You need to have postgress installed either localy or in docker, or in an external server to make this work. You need to make a database with the name bookstore and two users one with credentials bookstore, bookstore and one with the credentials flyway, flyway. You might need to configure the properties for your custom situation and you can choose differen more secure credentials for connecting with the database. I like postgres as a database because it is easy to setup but also is a real production database. I personally use this profile and not the H2 database

## Flyway 
I use flyway to mannage the different versions of my database https://flywaydb.org/. The folder src/main/resources/db/migration contains all the scripts needed to create the database. Running them one by one makes you end up with the database definition as intended by the program. Flyway automaticly runs these scripts at startup. If the program is already in a paticular state for example it already has run the first two scripts than flyway will only run the new scripts. Flyway stores which scripts are used in a table called flyway_schema_history. You should not modify any existing flyway scripts because they might already have run on an database and you want to guarantee consistency accross all your enviroments. If you spot a mistake in a flyway script you need to make a new script to restore it. I sometimes do change script in the development phase but never after the code has been merged and is on different developers or even a test and production enviroment.

## Hibernate and JPA
I use hibernate and JPA because it is need and it allows you to interact with javacode instead of SQL code which improves the readability and testability of code. However it is sometimes better for performance to write direct sql code tailored for the situation. But my bookstore does not need better performance and for me performance is only something you should consider when it starts being a problem in general readability and testability are more important concerns for me.

# Testing

## Itegration test with rest assured 
I use rest asured to make integration test, https://rest-assured.io/. Rest assured makes a request to the api endpoints. In this way you guarantee that you interact with the program in exactly the same way as external software or endusers do. You can even run the test against production code or code running on the test server. In spring a lot happens without you really writing code for it. There is a lot of stuff like security concerns or database access which is very important to your software but realy hard to test without intergration test. For example if I change the value of the property server.error.include-message to never my exception handling fails but I would not notice with any normal test, but these integration test do pick up on that. So for me the integration test are very important but they have clear downsides as well, they are slow but also hard to maintain. Because integration test interact with a real database you can end up in scenario's where one test interacts with a other, creating test which sometimes fail and sometimes succeed based on the order in which they run. In some teams they make a distingtion between unit test and integration test such that they can have different workflows running one ore the other but for me the integration test are so important that I cannot think of scenario's where you only want to run the unit test. 

## Good old unit test
I like unit test whenever they do not need mocking. In that case they are very often very easy and quick to write and very quick to run as well. I like writing a lot of them specially when the logic is complex. When a integration test breaks you know something is wrong somewhere in the code, with a unit test you know precisely where to look for a fix. They also show you the intend of the code and they make it easy to test more detailed scenario's than you would do with an integration test, in this project I mainly use them to test my logic.

## Service testing 
Very often the service layer either interacts with a database or a with a outside api or it has other dependencies. This makes them hard to test on their own. You have to options use integration test with everything in place or use mocks. I already use intergration test on the api level so it does not add anything to do them here as well, and mocks should be used with care. The downside of testing with mocks is that you do not test the real behavior but depend on the behavior you defined in the mock. Very often you test that a specific method is called with the correct parameters but the whole point of a unit test is that you should be able to change the implementation without breaking the test. I do use mocks but I try to avoid them in this paticulare project I do not test the sevice layer on its own, the unit test on the model level and the integration test on the api level are sufficient.

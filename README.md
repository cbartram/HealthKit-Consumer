# HealthKit-Consumer
Consumer Application to Receive published events from HealthKit's WatchOS application.

# Getting Started

To get started with this project clone the repository with `git clone https://github.com/cbartram/HealthKit-Consumer.git`

Run mongodb with: `$ docker run --name mongodb -p 27017:27017 -d -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=secret mongo:latest`
Connect to mongo with `$ mongo -u admin -p secret --authenticationDatabase admin metrics`

### Prerequisites & Installation

Before you can run this project you will need to install several software programs to your machine. If you already
have these programs you can omit this section.

- Java 11
- Gradle >= 6.3
- Docker
- MongoDB

#### Java 11
 
This project runs with Java version 11 and its required to get started developing with this application.
You can download Java 11 through [Homebrew](https://brew.sh) the popular package manager for Mac.

With [Homebrew](https://brew.sh) installed you can download and install Java with:
```shell script
$ brew update
$ brew tap homebrew/cask-versions
$ brew cask install homebrew/cask-versions/java11
```

You can validate your installation with:

```shell script
$ java -version
```

#### Gradle 

Once you have Java successfully installed you will need to download and install [Gradle](https://gradle.org). Gradle is a build manager
for Java and helps with managing dependencies, packaging up the executable Jar file, and running unit tests!

Install Gradle through [Homebrew](https://brew.sh) using the following command:

```shell script
$ brew install gradle
```

#### Docker

// TODO

#### Mongo DB

Lastly you will need to have [Mongo DB](https://www.mongodb.com/) installed since it is the primary database for this Application. Luckily you can
install [Mongo DB](https://www.mongodb.com/) with [Homebrew](https://brew.sh) by executing the following command: 

```shell script
$ brew tap mongodb/brew
$ brew install mongodb-community@4.2
```

### Installing

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system


## Architecture Diagram

The following image depicts the current architecture diagram for this system.

![Architecture Diagram](https://i.imgur.com/JJGP1Ti.png)

## Built With

* [Spring](http://spring.io) - The web framework used
* [Gradle](https://gradle.org) - Dependency Management & Build Tool
* [Java](https://www.java.com/en/) - Programming Language

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Christian Bartram** - *Initial work* - [cbartram](https://github.com/cbartram)

See also the list of [contributors](https://github.com/cbartram/Health-Consumer/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/gradle-plugin/reference/html/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## Acknowledgments

* Spring for making a great framework!

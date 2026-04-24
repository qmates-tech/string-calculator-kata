# String Calculator - Java Jetty API

Java API backend (embedded Jetty) implementation of the [String Calculator Kata](../README.md).

## Requirements

- Java 21+

## Build and run tests

Compile the project:

```
$ ./mvnw compile
```

Run the tests with:

```
$ ./mvnw test
```

## Try it

Start the server with:

```
$ ./mvnw compile exec:java
```

The server starts on `http://localhost:8080`. You can try it with a simple curl:

```curl
$ curl -X POST -d $'2,8,9' http://localhost:8080/add
```

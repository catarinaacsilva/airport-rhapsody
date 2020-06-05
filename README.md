# Airport Rhapsody - Synchronized Approach

An implementation in Java of the airport-rhapsody.

## The problem

Assume that there are five plane landings, each involving the arrival of six passengers, carrying a
maximum of two pieces of luggage in the plane hold and that the transfer bus has a capacity of three
seating places. Write a simulation of the life cycle of the passengers, the porter and the bus driver using
one of the models for thread communication and synchronization which have been studied: monitors or
semaphores and shared memory.
One aims for a distributed solution with multiple information sharing regions, written in Java, run in
Linux and which terminates. A logging file, which describes the evolution of the internal state of the
problem in a clear and precise way, must be included.

## Requirements

- Java 8
- Maven

## Run the simulation

Run the following command:

```bash
mvn -q clean compile exec:java
```

For convenience we also include a bash script that run the simulation 100 times:

```bash
./run.sh
```

## Compile javadoc

Run the following command:

```bash
mvn javadoc:javadoc
firefox target/site/apidocs/index.html
```

## Interaction Diagram

[Interaction Diagram](https://github.com/catarinaacsilva/airport-rhapsody/blob/master/interactionDiagram.pdf)

## Authors

* **Catarina Silva** - [catarinaacsilva](https://github.com/catarinaacsilva)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

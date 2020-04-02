# Airport Rhapsody - Message Passing Approach

An implementation in Java of the airport-rhapsody.


## The problem

Assume that there are five plane landings, each involving the arrival of six passengers, carrying a
maximum of two pieces of luggage in the plane hold and that the transfer bus has a capacity of three
seating places. Write a simulation of the life cycle of the passengers, the porter and the bus driver using
the client-server model with server replication, where the passengers, the porter and the bus driver are the
clients and access to the information sharing regions are the services provided to them by the servers.
The operations that were previously assigned to activities carried out in the information sharing
regions (for the already implemented concurrent version), must now be assigned to independent requests
performed on the servers through message passing. In each case, a connection has to be established, a
request has to be made, waiting for the reply will follow and the connection has to be closed.
One aims for a solution to be written in Java, to be run in Linux under TCP sockets in a concentrated
manner (on a single platform) and to terminate (it must contemplate service shutdown). A logging file,
that describes the evolution of the internal state of the problem in a clear and precise way, must be
included.

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

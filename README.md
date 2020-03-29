# airport-rhapsody

An implementation in Java of the airport-rhapsody.

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

## Authors

- **Catarina Silva**
- **Duarte Dias**

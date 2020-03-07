# Airport rhapsody


# Shared

- pt.ua.deti.Bus
- Collect a bag (não usar tipos de dados referências mas sim copiar o valor):
    - pt.ua.deti.Passenger
    - pt.ua.deti.Porter


## Implementation

- threads: 3 classes (passengers, porter, bus driver)

- 8 Monitors

## Requirements

- Java 8

## Compilation and execution

The whole project was done in Maven, as such to compile this simulation just run:
```bash
mvn clean compile
```

The javadoc can be generate with the following command:
```
mvn javadoc:javadoc
```

In order to run the simualtion simple execute the following command:
```
mvn exec:java -Dexec.mainClass="pt.ua.deti.Main"
```

## Authors

* **Catarina Silva** 
* **Duarte Dias**

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
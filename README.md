# dapr example - service invocation

The following scenario is implemented in this project.

![scenario](docs/scenario.png)

A client wants to have some information about the weather, the time and some news.
he retrieves these information via the _Overview service_.
The overview service collects these single information from three separate services.
But not by calling the services directly. Instead, the overview service requests its own
dapr sidecar to collect the information from the other services. 
The sidecar then requests the information from the other services sidecars.
This is the moment when the dapr service invocation comes into play.

## Get started

### 1. Build all services
```shell
Foo/gradlew buildFatJar -p Foo
Bar/gradlew buildFatJar -p Bar
Baz/gradlew buildFatJar -p Baz
```

### 2. Run each service alongside a dapr sidecar
```shell
dapr run --app-id foo-service --app-port 8080 --app-protocol http --dapr-http-port 3500 -- java -cp Foo/build/libs/fat.jar com.example.FooKt
dapr run --app-id bar-service --app-port 8081 --app-protocol http --dapr-http-port 3501 -- java -cp Bar/build/libs/fat.jar com.example.BarKt
dapr run --app-id baz-service --app-port 8082 --app-protocol http --dapr-http-port 3502 -- java -cp Baz/build/libs/fat.jar com.example.BazKt
```
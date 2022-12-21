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

### 1. Build each service
```shell
dashboard/gradlew buildFatJar -p dashboard
weather/gradlew buildFatJar -p weather
time/gradlew buildFatJar -p time
news/gradlew buildFatJar -p news
```

### 2. Run each service alongside a dapr sidecar
```shell
dapr run --app-id dashboard-service --app-port 8080 --app-protocol http --dapr-http-port 3500 -- java -cp dashboard/build/libs/fat.jar com.example.DashboardKt
dapr run --app-id weather-service --app-port 8081 --app-protocol http --dapr-http-port 3501 -- java -cp weather/build/libs/fat.jar com.example.WeatherKt
dapr run --app-id time-service --app-port 8082 --app-protocol http --dapr-http-port 3502 -- java -cp time/build/libs/fat.jar com.example.TimeKt
dapr run --app-id news-service --app-port 8083 --app-protocol http --dapr-http-port 3503 -- java -cp news/build/libs/fat.jar com.example.NewsKt
```
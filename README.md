# Tracemission server

![API Build and Deployment](https://github.com/tracemission/tracemission-server/workflows/API%20Build%20and%20Deployment/badge.svg?branch=master)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=tracemission_tracemission-server&metric=alert_status)](https://sonarcloud.io/dashboard?id=tracemission_tracemission-server)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Build and run native docker

Build: 
```
docker build -f src/main/docker/Dockerfile.multistage -t quarkus-quickstart/getting-started .
```

Run:
```
docker run -i --rm -p 6789:6789 quarkus-quickstart/getting-started
```



## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `tracemissionserver-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/tracemissionserver-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/tracemissionserver-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide.

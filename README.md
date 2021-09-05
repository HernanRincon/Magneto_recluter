# magneto_recluter Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .
## Considerations.

You need to have pre installed:
* Lombok library. you can find more information in this link https://projectlombok.org/
* maven build manager. you can find more information in this link https://maven.apache.org/index.html
* openjdk 11 or less than 16. you can find more information in this link  https://adoptopenjdk.net/ or Graalvm if you want to create a native executable (https://www.graalvm.org/docs/getting-started/)
* you need to have a environment variables for maven,java and graalvm(this is optiona).

# DynamoDB local instance

Just run it as follows:
`docker run --rm --name local-dynamo -p 8000:4569 -e SERVICES=dynamodb -e START_WEB=0 -d localstack/localstack`

DynamoDB listens on `localhost:8000` for REST endpoints.

Create an AWS profile for your local instance using AWS CLI:

```
$ aws configure --profile localstack
AWS Access Key ID [None]: test-key
AWS Secret Access Key [None]: test-secret
Default region name [None]: us-east-1
Default output format [None]:
```

## Create table

Create a DynamoDB table using AWS CLI and the localstack profile.
```
aws dynamodb create-table --table-name QuarkusFruits \
                          --attribute-definitions AttributeName=fruitName,AttributeType=S \
                          --key-schema AttributeName=fruitName,KeyType=HASH \
                          --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
                          --profile localstack --endpoint-url=http://localhost:8000
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvn compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvn package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvn package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvn package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvn package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/magneto_recluter-1.0.0-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Related Guides

- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

### Swagger-ui

In this url http://localhost:8080/q/swagger-ui/ you can find the basic swagger information

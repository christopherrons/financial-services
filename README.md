# Financial Services

This application consumes live market data from e.g. Bitstamp through it market data service and feeds it through a
trading-, surveillance-,
pricing-and risk engine. The backend pushed data through websockets and can be queried by the frontend through a REST
api which documented using Swagger and Spring Doc.

## Table of Content

* [Tech Stack](#tech-stack): The tech stack of the project.
* [Service](#services): High level overview of the application.
* [Market Data](#market-data): The data coming into the system.
* [Application DevOps](#application-devops): How to build, run and deploy the system.

## Tech Stack

* `Java 17`: Used for backend development.
    * `Spring/Spring-boot`: For REST API and event broadcasting.
* `Gradle`: Used as a build tool and package manager.
    * `Kotlin`: Used for configuring gradle.
* `Swagger`: Used for building the API documentation.
    * The documentation can be found at `http://<host>:8080/api/v1/swagger-ui/`
* `Typescript`: Used for frontend development.
* `React`: Framework used for building the frontend.

## Services

### Backend

* `Market Data Service`
    * Listen to and parses market data from e.g. Bitstamp and broadcast order events.
* `Trading Engine`
    * Listens to order broadcasts and maintains and matches an orderbook, broadcasting trades when order are matched.
* `Surveillance Engine`
    * Listens to orders and trade broadcast and run the through the algorithmic and statistics based alerts. Broad
      triggered alert broadcasts.
* `Refdata Service`
    * Listen to orders and trades broadcasts, created statistics, updates portfolios, participant and instruments.
      Queries for historical prices and yields using open API`s at NASDAQ and Yahoo.
* `Pricing Engine`
    * Listens to orders and trade broadcast to update instrument margin prices. Broadcasts a price collection with
      margin prices, yield and historical prices on a set interval.
* `Risk Engine`
    * Listens to price collection broadcasts and does risk calculation such as Monte Carlo Value At Risk using portfolio
      refdata. Broadcast margin calculation Results.
* `Websocket`
    * Listens to order, trade, triggered alert, margin calculation broadcasts and pushes the data out in a relevant
      format using websockets.
* `Rest Api`
    * Endpoints to request refdata, initiate subscriptions and creating orders among others.

### Front End

* `Live Market Data`
    * WIP: View live market data, orderbook depth, trades and price graph.
* `Live Survaillane Alerts`
    * WIP: View live surveillance alerts.
* `Requested Ref Data`
    * WIP: View risk portfolios and value at risk.
* `Requested Ref Data`
    * WIP: View refdata per request.

## Market Data

Currently, only Bitstamp public anonymous orders are integrated to the system. The events enter by asynchronously
listening to their [websocket](https://www.bitstamp.net/websocket/v2/).

## Application DevOps

### Staring the Application

Start the application from the root folder by running`./gradlew bootRun`.

### Building the Application

Build the application from the root folder by running`/gradlew packageRelease -PreleaseVersion=<release-version>` to
build and bundle the application jar and relevant deploy
scripts.

### Deploying the Application

The application can be deployed to a remote machine using the `deploy` task. See [deploy.md](deploy/deploy.md)
and [jenkins.md](cicd/jenkins/jenkins.md) for further documentation.



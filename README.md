# Spring Boot Flickr Integration

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.3.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-6.4.1-blue.svg)](https://gradle.org/)

A minimal Spring Boot starter project demonstrating how to integrate the Flickr API in Java using **Flickr4Java** and **ScribeJava** OAuth 1.0a authentication. It configures an authenticated `Flickr` Spring bean at startup and exposes a sample REST endpoint for fetching a user's public photos.

## Key Features

- **Spring Boot Flickr Bean Configuration** — `FlickrConfig` reads `flickr.apiKey` and `flickr.secretKey` from `application.properties` and registers an authenticated `Flickr` REST client bean.
- **Interactive OAuth 1.0a Flow** — Uses ScribeJava (`FlickrApi` with `READ` permission) to generate a Flickr authorization URL, prompt for the verifier code in the console, exchange it for an access token, and validate it via `checkToken`.
- **Sample REST Endpoint** — `HomeController` exposes `GET /` to query a Flickr user's public photos via `flickr.getPeopleInterface().getPublicPhotos(userId, size, page)` with configurable pagination parameters.
- **Gradle Wrapper Included** — Uses Gradle 6.4.1 via `gradlew` / `gradlew.bat` for consistent builds without a separate local Gradle installation.

## Tech Stack

- **Language:** Java 8
- **Framework:** Spring Boot `2.3.3.RELEASE` (`spring-boot-starter-web`, `spring-boot-starter-test`)
- **Flickr Client & OAuth:** [Flickr4Java](https://github.com/boncey/Flickr4Java) `3.0.2` (with ScribeJava OAuth 1.0a)
- **Build Tool:** Gradle `6.4.1` (Gradle Wrapper)
- **Testing:** JUnit 5 (`useJUnitPlatform`)

## Project Structure

```text
springboot-flickr/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── gradle/wrapper/
└── src/
    ├── main/
    │   ├── java/com/example/demo/
    │   │   ├── Application.java        # Spring Boot entry point
    │   │   ├── FlickrConfig.java       # OAuth 1.0a setup & Flickr @Bean
    │   │   └── HomeController.java     # GET / endpoint fetching public photos
    │   └── resources/
    │       └── application.properties  # Flickr API key & secret configuration
    └── test/
        └── java/com/example/demo/
            └── ApplicationTests.java   # Spring Boot context load test
```

## Prerequisites

- **JDK 8** (matching `sourceCompatibility = '1.8'` and Gradle 6.4.1)
- A **Flickr API Key** and **Secret Key** obtained from the [Flickr App Garden](https://www.flickr.com/services/apps/create/)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/sinhsac/springboot-flickr.git
cd springboot-flickr
```

### 2. Configure Flickr API credentials

Update `src/main/resources/application.properties` with your Flickr API key and secret:

```properties
flickr.apiKey=YOUR_FLICKR_API_KEY
flickr.secretKey=YOUR_FLICKR_SECRET_KEY
```

### 3. Build and run the application

Because `FlickrConfig` reads the OAuth verifier code interactively from standard input (`System.in`) during bean initialization, build the executable JAR and run it directly in an interactive terminal (or run `Application.main` from your IDE):

```bash
# macOS / Linux
./gradlew bootJar -x test
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar

# Windows
gradlew.bat bootJar -x test
java -jar build\libs\demo-0.0.1-SNAPSHOT.jar
```

> **Note:** Running `./gradlew test` executes `ApplicationTests`, which loads the full Spring context including the interactive `FlickrConfig` bean. Provide valid credentials or mock the `Flickr` bean if running automated tests non-interactively.

### 4. Complete OAuth 1.0a authorization

During startup, the application prints a Flickr authorization URL and prompts for a verifier code:

```text
https://www.flickr.com/services/oauth/authorize?oauth_token=...
Paste the verifier code here:
>>
```

1. Open the printed URL in your browser and authorize read access.
2. Copy the verifier code displayed by Flickr and paste it into the terminal prompt.
3. Once verified, the embedded server starts on `http://localhost:8080`.

### 5. Call the sample endpoint

Send a `GET` request to `/` with optional query parameters:

```bash
curl "http://localhost:8080/?userId=100906270@N03&page=1&size=100"
```

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `userId` | `String` | `100906270@N03` | Flickr NSID of the target user |
| `page` | `Integer` | `1` | Page number of results to retrieve |
| `size` | `Integer` | `100` | Number of public photos per page |

The controller fetches the user's public photos into a `PhotoList<Photo>` via `flickr.getPeopleInterface().getPublicPhotos(userId, size, page)` and returns `"home"`.

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

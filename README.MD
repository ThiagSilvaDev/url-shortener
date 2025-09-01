# URL Shortener

## Overview

This is a simple URL shortener service built with Spring Boot. It allows users to shorten long URLs into shorter, more manageable links.

## Features

*   Shorten long URLs
*   Redirect to original URLs using short URLs
*   Uses H2 database for storage
*   Uses MurmurHash3 for generating short URLs

## Technologies Used

*   Java 17
*   Spring Boot
*   Maven
*   H2 Database
*   SLF4J for logging
*   Guava library for hashing

## Getting Started

### Prerequisites

*   Java 17 or higher
*   Maven

### Installation

1.  Clone the repository:

    ```bash
    git clone https://github.com/ThiagSilvaDev/url-shortener
    ```
2.  Navigate to the project directory:

    ```bash
    cd url-shortener
    ```

### Running the Application

1.  Build the application:

    ```bash
    mvn clean install
    ```
2.  Run the application:

    ```bash
    mvn spring-boot:run
    ```

The application will be accessible at `http://localhost:8080`.

### Using the Service

#### Shorten a URL

`POST /shortener`

Request body:

```json
{
  "longUrl": "https://www.example.com"
}
```

Response:

```json
{
  "shortUrl": "http://localhost:8080/a1b2c3d4"
}
```

#### Redirect to a Long URL

In your browser paste the URL received in the response, for example:

`http://localhost:8080/a1b2c3d4`

This will redirect you to the original long URL.

## TODOs

*   Create a mapper for the request
*   Improve the response
*   Change the approach to generate the location
*   Create a custom exception for this
*   Improve the hashing algorithm to avoid collisions
*   Change the approach to handle not found

## License

[MIT](LICENSE)
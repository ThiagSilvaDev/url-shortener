# URL Shortener

## Overview

This is a simple URL shortener service built with Spring Boot. It allows users to shorten long URLs into shorter, more
manageable links.

## Features

* Shorten long URLs
* Redirect to original URLs using short URLs
* Uses Postgres for storage
* Uses base62 hashing for generating short URLs
* Rate limiting to prevent abuse
* Caching with Redis for improved performance

## Technologies Used

* Java 21
* Spring Boot
* Maven
* PostgreeSQL
* Redis for caching
* Flyway for database migrations
* Nginx as a reverse proxy
* Docker for containerization
* SLF4J for logging

## Getting Started

### Prerequisites

* Java 21 or higher
* Maven
* Docker and docker compose

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/ThiagSilvaDev/url-shortener
   ```
2. Navigate to the project directory:

   ```bash
   cd url-shortener
   ```

### Running the Application

Run the application:

   ```bash
   docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d --build
   ```

The application will be accessible at `http://localhost:4200`.

### Using the Service API

The API will be accessible at `http://localhost:8080`.

#### Shorten a URL

`POST /api/v1/shortener`

Request body:

```json
{
  "longUrl": "https://www.example.com"
}
```

Response:

```json
{
  "longUrl": "https://www.example.com",
  "shortUrl": "http://localhost:8080/b"
}
```

#### Redirect to a Long URL

In your browser paste the URL received in the response, for example:

`http://localhost:8080/b`

This will redirect you to the original long URL.

## License

[MIT](LICENSE)
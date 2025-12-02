# Trading system
Simple MVP of a conceptual trading system built with: 
- Java 21
- Spring Boot 3.8.x
- Spring 5
- Redis
- Postgresql
- Kafka
- WireMock
- Reactor
- R2DBC


# Default mappings for docker compose:
```
+------------+----------------+-----------------+
| Name       | Container Port | Mapping         |
+------------+----------------+-----------------+
| db         | 5432           | 172.18.0.1:5432 |
+------------+----------------+-----------------+
| broker     | 9092           | 172.18.0.1:9092 |
+------------+----------------+-----------------+
| kafka-ui-1 | 8080           | 172.18.0.1:8090 |
+------------+----------------+-----------------+
| redis      | 6379           | 172.18.0.1:6379 |
+------------+----------------+-----------------+
| wiremock   | 8080           | 172.18.0.1:3001 |
+------------+----------------+-----------------+
| app        | 8080           | 172.18.0.1:8080 |
+------------+----------------+-----------------+
```

In order to deploy project with all of dependencies just run `./gradlew composeUp`.

# Known Issues:
- Lack of kafka integration tests
- Incorrect `@Column` annotation for R2DBC (not taking into account overwriting column's name)
- Missing functionality of accepting the order (changing status to `Filled` / `Expired`)
- Case sensitivity in endpoint url (`GPW` instead of `gpw`) - I wanted to use proper enum, didn't figure it out how to parse it with different casing like `gPw` or `gpw`
- Hardcoded accountId
- Missing fallback cases for most cases (like no response from external API - in this case wiremock)
- Lack of "production-grade" exception handling
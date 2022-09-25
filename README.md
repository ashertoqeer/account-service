# Account Service

- Simple spring boot account management service.
- Supports account creation and funds transfer.

### Run Application

- Run `mvn clean install` to install all dependencies.
- Run `mvn spring-boot:run.` to run application.

By default, application will run using in memory H2. However, you can define your own data source in `application.properties`.

### Swagger
`http://localhost:8080/swagger-ui/index.html`

### Docker
 - Run `mvn package` to create jar file
 - Run `docker image build -t account-service:latest .` in root directory to build image from docker file
 - Run `docker run -p 8080:8080 account-service:latest`

Application will start running in docker container with default H2 database
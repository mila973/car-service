# car-service
Vehicle repairing scheduling and managing service

### Technology stack:
  1. Java Spring
  2. Postgresql for database
  3. Swagger 2.0 for documentation

### Prerequisites to run:
  1. install docker

### Steps to run:
  2. in `docker-compose.yml` file change car-service variable `DEFECT_URL` to defect container url
  1. terminal execute: `docker-compose up -d`
  
### Build docker image:
  1. terminal execute: ./gradlew dockerBuildImage

### Documentation

Documentation provided by Swagger, url: `http://<car-service url>/swagger-ui.html#/`

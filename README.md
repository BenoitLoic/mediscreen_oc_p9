# Mediscreen

## Add project

- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line)
  or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.com/LoicBenoit/mediscreen.git
git branch -M main
git push -uf origin main
```

## Technical

- Java 17
- maven 3.8+
- docker
- docker-compose

## Test

run test with

```
cd MODULE_TO_TEST
mvn clean test
```

run integration test with

```
cd MODULE_TO_TEST
mvn clean verify
```

***

## Name

1. Mediscreen patient info service.
2. Mediscreen patient history service.
3. Mediscreen patient assessment service.

## Description

1. Mediscreen microservice for Patient information. Run with spring boot on docker container with containerized
   Mysql<br>
   database. This service contain a REST API to interact with patient information.<br>
2. Mediscreen microservice for Patient history. Run with spring boot on docker container with containerized MongoDb<br>
   database. This service contain a REST API to interact with patient history that contains notes(Practitioner's<br>
   notes/recommendations).
3. Mediscreen microservice for Patient assessment. Run with spring boot on docker container. This service contain a<br>
   REST API that calls patient info service and patient history service to return an assessment of the patient about<br>
   his risk to develop a diabete.


## Installation / deploy

Install docker and docker-compose.

```
cd project_repo/mediscreen
docker-compose up --build
```

## Usage

1. Access front at http://localhost:4200/ <br>
2. Access API endpoints at  
   - http://localhost:8081/patient/{endpoint}
   - http://localhost:8082/patHistory/{endpoint}
   - http://localhost:8082/patHistory/note/{endpoint}
   - http://localhost:8080/assess/{endpoint}

## API Doc

API documentations are located in /doc . Import in Postman, then select "view documentation".
<br>API doc can also be seen online:

1. patientInfo : https://documenter.getpostman.com/view/13096235/UzBpK5uS
2. patientHistory: https://documenter.getpostman.com/view/13096235/UzBpKm6f
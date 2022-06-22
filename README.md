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

## Test

run test with

```
mvn clean test
```

***

## Name

1. Mediscreen patient info service.
2. Mediscreen patient history service.

## Description

1. Mediscreen microservice for Patient information. Run with spring boot on docker container with containerized Mysql<br>
  database. This service contain a REST API to interact with patient information.<br>
2. Mediscreen microservice for Patient history. Run with spring boot on docker container with containerized MongoDb<br>
  database. This service contain a REST API to interact with patient history that contains notes(Practitioner's<br>
  notes/recommendations).

## Installation / deploy

Install docker and docker-compose.

```
cd project_repo/mediscreen
docker-compose up --build
```

## Usage

1. Access front at http://localhost:8081/ <br>
2. Access API endpoints at http://localhost:8081/patient/{endpoint}

## API Doc
API documentations are located in /doc . Import in Postman, then select "view documentation".
<br>API doc can also be seen online:
1. patientInfo : https://documenter.getpostman.com/view/13096235/UzBpK5uS
2. patientHistory: https://documenter.getpostman.com/view/13096235/UzBpKm6f
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

- Mediscreen patient info service.
- Mediscreen patient history service.

## Description

- Mediscreen microservice for Patient information. Run with spring boot on docker container with containerized Mysql<br>
  database. This service contain a REST API to interact with patient information.<br>
- Mediscreen microservice for Patient history. Run with spring boot on docker container with containerized MongoDb<br>
  database. This service contain a REST API to interact with patient history that contains notes(Practitioner's<br>
  notes/recommendations).

## Installation / deploy

Install docker and docker-compose.

```
cd project_repo/mediscreen
docker-compose up --build
```

## Usage

Access front at http://localhost:8081/ <br>
Access API endpoints at http://localhost:8081/patient/{endpoint}


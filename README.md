# Mediscreen



## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add project

- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.com/LoicBenoit/mediscreen.git
git branch -M main
git push -uf origin main
```

## Test 

run test with 
```
mvn clean test
```

***

# Editing this README

When you're ready to make this README your own, just edit this file and use the handy template below (or feel free to structure it however you want - this is just a starting point!). Thank you to [makeareadme.com](https://www.makeareadme.com/) for this template.

## Suggestions for a good README
Every project is different, so consider which of these sections apply to yours. The sections used in the template are suggestions for most open source projects. Also keep in mind that while a README can be too long and detailed, too long is better than too short. If you think your README is too long, consider utilizing another form of documentation rather than cutting out information.

## Name
Mediscreen patient info service.

## Description
Mediscreen microservice for Patient information. Run with spring boot on docker container with containerized Mysql database. This service contain a REST API to interact with patient information. 


## Installation
Install docker and docker-compose.  
```
cd project_repo/mediscreen
docker-compose up --build
```

## Usage
Access front at http://localhost:8081/ <br>
Access API endpoints at http://localhost:8081/patient/{endpoint}


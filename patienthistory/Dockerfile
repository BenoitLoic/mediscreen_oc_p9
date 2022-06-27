FROM maven:3.8.6-eclipse-temurin-17-alpine
WORKDIR /patienthistory
COPY . .
RUN mvn clean package
CMD mvn spring-boot:run
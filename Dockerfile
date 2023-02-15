FROM maven:3.8.5-jdk-8
WORKDIR /businessService
COPY . .
CMD ["mvn", "spring-boot:run","-Dmaven.test.skip=true"]
EXPOSE 8081
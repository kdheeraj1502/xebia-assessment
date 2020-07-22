FROM openjdk:8
EXPOSE 8085
ADD target/article-service-docker.jar article-service-docker.jar
ENTRYPOINT ["java","-jara","/article-service-docker.jar"]
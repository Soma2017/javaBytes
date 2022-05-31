FROM openjdk:11
EXPOSE 8080
ADD target/tweetAppService.jar tweetAppService.jar
ENTRYPOINT ["java","-jar","/tweetAppService.jar"]
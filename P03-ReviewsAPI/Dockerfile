FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD target/reviews-0.0.1-SNAPSHOT.jar reviews-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","reviews-0.0.1-SNAPSHOT.jar"]
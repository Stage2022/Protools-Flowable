FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD target/flowablePOC-0.0.1-SNAPSHOT.jar flowablePOC-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/flowablePOC-0.0.1-SNAPSHOT.jar"]

FROM openjdk:8

LABEL maintainer="Kunj Bhagtani"

RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz
  
#port number
EXPOSE 8086

# The application's jar file
ARG JAR_FILE=target/ShoppingRetailService-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} ShoppingRetailService-0.0.1-SNAPSHOT.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","ShoppingRetailService-0.0.1-SNAPSHOT.jar"]
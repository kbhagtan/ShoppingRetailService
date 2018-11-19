FROM openjdk:8

LABEL maintainer="Kunj Bhagtani"

EXPOSE 8084

# The application's jar file
ARG JAR_FILE=target/ShoppingRetailService-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} ShoppingRetailService-0.0.1-SNAPSHOT.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","ShoppingRetailService-0.0.1-SNAPSHOT.jar"]
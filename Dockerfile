FROM openjdk:17.0.1-jdk-slim
EXPOSE 5500
ADD build/libs/moneytransferservice-0.0.1-SNAPSHOT.jar moneytransfer.jar
CMD ["java" , "-jar", "moneytransfer.jar"]
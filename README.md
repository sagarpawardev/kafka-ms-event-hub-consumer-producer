Simple consumer producer example for "Microsoft event hub" and "kafka" connection with with kafka protocol

## Introduction
This repository create a spring boot web service to produce and consume data either from Apache Kafka or Microsoft Event Hub using the kafka protocol.
This means that using the same codebase you can switch between kafaka and microsoft event hub.
Producer -> Produces a message
Consumer -> Consumes atleast 3 messages (Configure this setting from CustomKafkaConsumer.java)

## Prerequisites
1. Java-jdk
2. Running Kafka or Microsoft Event Hub
3. Any IDE (preferably intelliJ)
4. Postman (for calling web services)

## Installation
1. Clone the repository
2. Search "TODO:" in all files and modify string as required
2. Open IntelliJ. Goto File -> Project from Existing Source -> Open Cloned Directory -> Choose External Model "maven" -> Proceed nomally with other steps
3. Open KafkaApplication.java and Run it using "Run" Button or 'Control + R' in mac
4. Open Postman. Add new request and add url "localhost:9000/kafka/publish?message=Hello_World" and click Send. It should give Http Status '200 OK'. This will produce a message
5. Try 
5. Add another consume request "localhost:9000/kafka/consume" and click Send. It should return list of consumed message

//TODO: 

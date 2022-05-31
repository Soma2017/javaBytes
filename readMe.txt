kafka.serverConfig=localhost:2181
kafka.bootStrapServer=localhost:9092
kafka.topicName=message

Kafka Commands: -
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

.\bin\windows\kafka-server-start.bat .\config\server.properties

.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic message --from-beginning
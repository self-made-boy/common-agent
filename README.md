隔离 Kafka、xxl、redis多个实例共用一个资源，在实例层面分区

使用方式
```shell
mvn clean package
java  -javaagent:/yourpath/agent.jar \
-Dagent.kafka.env.value=xxx \
-Dagent.kafka.header.key=xxx \
-Dagent.redis.env.value=xxx \
-Dagent.xxljob.env.value=xxx \
-jar app.jar
```

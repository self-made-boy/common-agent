package com.github.selfmadeboy.agent.kafka;

class Constants {

    static final String KAFKA_ENV_KEY_NAME = "agent.kafka.env.value";
    static final String KAFKA_HEADER_KEY_NAME = "agent.kafka.header.key";

    static final String BATCH_MESSAGE_LISTENER_QUALIFIED_NAME = "org.springframework.kafka.listener.BatchMessageListener";
    static final String MESSAGE_LISTENER_QUALIFIED_NAME = "org.springframework.kafka.listener.MessageListener";

    static final String MESSAGE_LISTENER_METHOD_NAME = "onMessage";
    static final String MESSAGE_LISTENER_METHOD_SIGNATURE_1 = "(Lorg/apache/kafka/clients/consumer/ConsumerRecord;)V";
    static final String MESSAGE_LISTENER_METHOD_SIGNATURE_2 = "(Lorg/apache/kafka/clients/consumer/ConsumerRecord;Lorg/springframework/kafka/support/Acknowledgment;)V";
    static final String MESSAGE_LISTENER_METHOD_SIGNATURE_3 = "(Lorg/apache/kafka/clients/consumer/ConsumerRecord;Lorg/apache/kafka/clients/consumer/Consumer;)V";
    static final String MESSAGE_LISTENER_METHOD_SIGNATURE_4 = "(Lorg/apache/kafka/clients/consumer/ConsumerRecord;Lorg/springframework/kafka/support/Acknowledgment;Lorg/apache/kafka/clients/consumer/Consumer;)V";

    static final String BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_1 = "(Ljava/util/List;)V";
    static final String BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_2 = "(Ljava/util/List;Lorg/springframework/kafka/support/Acknowledgment;)V";
    static final String BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_3 = "(Ljava/util/List;Lorg/apache/kafka/clients/consumer/Consumer;)V";
    static final String BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_4 = "(Ljava/util/List;Lorg/springframework/kafka/support/Acknowledgment;Lorg/apache/kafka/clients/consumer/Consumer;)V";
    static final String BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_5 = "(Lorg/apache/kafka/clients/consumer/ConsumerRecords;Lorg/springframework/kafka/support/Acknowledgment;Lorg/apache/kafka/clients/consumer/Consumer;)V";


    static final String PRODUCER_QUALIFIED_NAME = "org.apache.kafka.clients.producer.Producer";
    static final String PRODUCER_METHOD_NAME = "send";
    static final String PRODUCER_METHOD_SIGNATURE_1 = "(Lorg/apache/kafka/clients/producer/ProducerRecord;)Ljava/util/concurrent/Future;";
    static final String PRODUCER_METHOD_SIGNATURE_2 = "(Lorg/apache/kafka/clients/producer/ProducerRecord;Lorg/apache/kafka/clients/producer/Callback;)Ljava/util/concurrent/Future;";
}

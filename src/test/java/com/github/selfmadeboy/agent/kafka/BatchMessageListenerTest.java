package com.github.selfmadeboy.agent.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

public class BatchMessageListenerTest<K,V> implements BatchMessageListener<K,V> {
    /**
     * Invoked with data from kafka.
     *
     * @param data the data to be processed.
     */
    @Override
    public void onMessage(List<ConsumerRecord<K, V>> data) {
        System.out.println(data);
    }

    /**
     * Invoked with data from kafka. The default implementation throws
     * {@link UnsupportedOperationException}.
     *
     * @param data           the data to be processed.
     * @param acknowledgment the acknowledgment.
     */
    @Override
    public void onMessage(List<ConsumerRecord<K, V>> data, Acknowledgment acknowledgment) {
        System.out.println(data);
        System.out.println(acknowledgment);

    }

    /**
     * Invoked with data from kafka and provides access to the {@link Consumer}. The
     * default implementation throws {@link UnsupportedOperationException}.
     *
     * @param data     the data to be processed.
     * @param consumer the consumer.
     * @since 2.0
     */
    @Override
    public void onMessage(List<ConsumerRecord<K, V>> data, Consumer<?, ?> consumer) {
        System.out.println(data);
        System.out.println(consumer);
    }

    /**
     * Invoked with data from kafka and provides access to the {@link Consumer}. The
     * default implementation throws {@link UnsupportedOperationException}.
     *
     * @param data           the data to be processed.
     * @param acknowledgment the acknowledgment.
     * @param consumer       the consumer.
     * @since 2.0
     */
    @Override
    public void onMessage(List<ConsumerRecord<K, V>> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        System.out.println(data);
        System.out.println(acknowledgment);
        System.out.println(consumer);
    }

    /**
     * Listener receives the original {@link ConsumerRecords} object instead of a
     * list of {@link ConsumerRecord}.
     *
     * @param records        the records.
     * @param acknowledgment the acknowledgment (null if not manual acks)
     * @param consumer       the consumer.
     * @since 2.2
     */
    @Override
    public void onMessage(ConsumerRecords records, Acknowledgment acknowledgment, Consumer consumer) {
        System.out.println(records);
        System.out.println(acknowledgment);
        System.out.println(consumer);
    }

    /**
     * Return true if this listener wishes to receive the original {@link ConsumerRecords}
     * object instead of a list of {@link ConsumerRecord}.
     *
     * @return true for consumer records.
     * @since 2.2
     */
    @Override
    public boolean wantsPollResult() {
        return BatchMessageListener.super.wantsPollResult();
    }
}

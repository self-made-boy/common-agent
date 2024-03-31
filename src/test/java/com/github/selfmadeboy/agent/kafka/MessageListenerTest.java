package com.github.selfmadeboy.agent.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

public class MessageListenerTest<K,V> implements MessageListener<K,V> {
    /**
     * Invoked with data from kafka.
     *
     * @param data the data to be processed.
     */
    @Override
    public void onMessage(ConsumerRecord<K, V> data) {
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
    public void onMessage(ConsumerRecord<K, V> data, Acknowledgment acknowledgment) {
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
    public void onMessage(ConsumerRecord<K, V> data, Consumer<?, ?> consumer) {
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
    public void onMessage(ConsumerRecord<K, V> data, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        System.out.println(data);
        System.out.println(acknowledgment);
        System.out.println(consumer);
    }
}

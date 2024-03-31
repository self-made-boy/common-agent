package com.github.selfmadeboy.agent.kafka;

import com.github.selfmadeboy.agent.TransformUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Assistant {

    public static <K, V> org.apache.kafka.clients.consumer.ConsumerRecord<K, V> filter(org.apache.kafka.clients.consumer.ConsumerRecord<K, V> record,
                                                                                       org.springframework.kafka.support.Acknowledgment ack,
                                                                                       org.apache.kafka.clients.consumer.Consumer<K, V> consumer) {

        org.apache.kafka.common.header.Header _header = record.headers().lastHeader(Constants.KAFKA_HEADER_KEY_NAME);
        if (_header == null && !hasEnv()) {
            return record;
        }

        if (_header != null && new String(_header.value()).equals(getEnv())) {
            return record;
        }
        log.info("agent skip the record,topic:{},partition:{},offset:{}", record.topic(), record.partition(), record.offset());
        if (ack != null) {
            log.info("agent ack the record by Acknowledgment,topic:{},partition:{},offset:{}", record.topic(), record.partition(), record.offset());
            ack.acknowledge();
        } else if (consumer != null) {
            log.info("agent commit the record by consumer,topic:{},partition:{},offset:{}", record.topic(), record.partition(), record.offset());
            java.util.HashMap<org.apache.kafka.common.TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata> offsets = new java.util.HashMap<>();
            offsets.put(new org.apache.kafka.common.TopicPartition(record.topic(), record.partition()), new org.apache.kafka.clients.consumer.OffsetAndMetadata(record.offset() + 1));
            consumer.commitSync(offsets);
        }

        return null;
    }


    public static <K, V> java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>> filterList(java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>> records,
                                                                                                           org.springframework.kafka.support.Acknowledgment ack,
                                                                                                           org.apache.kafka.clients.consumer.Consumer<K, V> consumer) {
        java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>> ret = new java.util.ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            org.apache.kafka.clients.consumer.ConsumerRecord<K, V> _record = records.get(i);

            org.apache.kafka.common.header.Header _header = _record.headers().lastHeader(Constants.KAFKA_HEADER_KEY_NAME);
            if (_header == null && !hasEnv()) {
                ret.add(_record);
            }

            if (_header != null && new String(_header.value()).equals(getEnv())) {
                ret.add(_record);
            }

            log.info("agent skip a record of batch list,topic:{},partition:{},offset:{}", _record.topic(), _record.partition(), _record.offset());

        }

        if (ret.isEmpty()) {
            log.info("agent skip all {} records", records.size());

            if (ack != null) {
                log.info("agent ack all {} records by Acknowledgment", records.size());
                ack.acknowledge();
            } else if (consumer != null) {
                log.info("agent ack all {} records by consumer", records.size());
                java.util.Map<String, java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>>> _collect = records.stream().collect(java.util.stream.Collectors.groupingBy(org.apache.kafka.clients.consumer.ConsumerRecord::topic));
                java.util.Map<String, java.util.Map<Integer, Long>> map = new java.util.HashMap<>();
                _collect.forEach((k, v) -> {

                    java.util.Map<Integer, java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>>> po = v.stream().collect(java.util.stream.Collectors.groupingBy(org.apache.kafka.clients.consumer.ConsumerRecord::partition));
                    java.util.Map<Integer, Long> tpoff = new java.util.HashMap<>();
                    po.forEach((pa, offset) -> {
                        java.util.OptionalLong optional = offset.stream().mapToLong(org.apache.kafka.clients.consumer.ConsumerRecord::offset).max();
                        tpoff.put(pa, optional.getAsLong());
                    });
                    map.put(k, tpoff);


                });

                java.util.HashMap<org.apache.kafka.common.TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata> offsets = new java.util.HashMap<>();
                map.forEach((topic, pto) -> {
                    pto.forEach((p, o) -> {
                        offsets.put(new org.apache.kafka.common.TopicPartition(topic, p), new org.apache.kafka.clients.consumer.OffsetAndMetadata(o + 1));
                    });
                });
                consumer.commitSync(offsets);
            }
        }

        return ret;
    }

    public static <K, V> org.apache.kafka.clients.consumer.ConsumerRecords<K, V> filterRecords(org.apache.kafka.clients.consumer.ConsumerRecords<K, V> records,
                                                                                               org.springframework.kafka.support.Acknowledgment ack,
                                                                                               org.apache.kafka.clients.consumer.Consumer<K, V> consumer) {
        java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>> list = new java.util.ArrayList<>();
        records.iterator().forEachRemaining(list::add);

        java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>> consumerRecords = filterList(list, ack, consumer);
        if (!consumerRecords.isEmpty()) {
            java.util.Map<org.apache.kafka.common.TopicPartition, java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>>> tpMap = new java.util.HashMap<>();
            for (org.apache.kafka.clients.consumer.ConsumerRecord<K, V> consumerRecord : consumerRecords) {
                String topic = consumerRecord.topic();
                int partition = consumerRecord.partition();
                org.apache.kafka.common.TopicPartition topicPartition = new org.apache.kafka.common.TopicPartition(topic, partition);
                if (tpMap.containsKey(topicPartition)) {
                    tpMap.get(topicPartition).add(consumerRecord);
                } else {

                    java.util.List<org.apache.kafka.clients.consumer.ConsumerRecord<K, V>> l = new java.util.ArrayList<>();
                    l.add(consumerRecord);
                    tpMap.put(topicPartition, l);
                }
            }
            return new org.apache.kafka.clients.consumer.ConsumerRecords<>(tpMap);
        }

        return null;
    }


    public static boolean hasEnv() {
        return TransformUtils.hasConfigByKey(Constants.KAFKA_ENV_KEY_NAME);
    }

    public static String getEnv() {
        return TransformUtils.getConfigByKey(Constants.KAFKA_ENV_KEY_NAME);
    }


}

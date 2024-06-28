package com.github.selfmadeboy.agent.redis;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Constants {


    static final  String ASSIST_CLASS_NAME = "com.github.selfmadeboy.agent.redis.AssistantRedisSerializer";
    static final String REDIS_ENV_KEY_NAME = "agent.redis.env.value";
    static final String REDIS_TEMPLATE_CLASS_NAME = "org.springframework.data.redis.core.RedisTemplate";

    static final String AFTER_PROPERTIES_SET_METHOD_NAME = "afterPropertiesSet";
    static final String AFTER_PROPERTIES_SET_METHOD_SIGNATURE = "()V";
    static final String SET_KEY_SERIALIZER_METHOD_NAME = "setKeySerializer";
    static final String SET_KEY_SERIALIZER_METHOD_SIGNATURE = "(Lorg/springframework/data/redis/serializer/RedisSerializer;)V";


    static final String REDISSON_CLIENT_CLASS_NAME = "org.redisson.api.RedissonClient";

    static final List<String> methodSignatures = new ArrayList<>();


    static {
        methodSignatures.add("getTimeSeries");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RTimeSeries;");
        methodSignatures.add("getTimeSeries");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RTimeSeries;");
        methodSignatures.add("getStream");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RStream;");
        methodSignatures.add("getStream");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RStream;");
        methodSignatures.add("getRateLimiter");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RRateLimiter;");
        methodSignatures.add("getBinaryStream");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RBinaryStream;");
        methodSignatures.add("getGeo");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RGeo;");
        methodSignatures.add("getGeo");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RGeo;");
        methodSignatures.add("getSetCache");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RSetCache;");
        methodSignatures.add("getSetCache");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RSetCache;");
        methodSignatures.add("getMapCache");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RMapCache;");
        methodSignatures.add("getMapCache");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;Lorg/redisson/api/MapOptions;)Lorg/redisson/api/RMapCache;");
        methodSignatures.add("getMapCache");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RMapCache;");
        methodSignatures.add("getMapCache");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/api/MapOptions;)Lorg/redisson/api/RMapCache;");
        methodSignatures.add("getBucket");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RBucket;");
        methodSignatures.add("getBucket");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RBucket;");
        methodSignatures.add("getJsonBucket");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/codec/JsonCodec;)Lorg/redisson/api/RJsonBucket;");
        methodSignatures.add("getHyperLogLog");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RHyperLogLog;");
        methodSignatures.add("getHyperLogLog");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RHyperLogLog;");
        methodSignatures.add("getList");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RList;");
        methodSignatures.add("getList");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RList;");
        methodSignatures.add("getListMultimap");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RListMultimap;");
        methodSignatures.add("getListMultimap");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RListMultimap;");
        methodSignatures.add("getListMultimapCache");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RListMultimapCache;");
        methodSignatures.add("getListMultimapCache");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RListMultimapCache;");
        methodSignatures.add("getLocalCachedMap");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/api/LocalCachedMapOptions;)Lorg/redisson/api/RLocalCachedMap;");
        methodSignatures.add("getLocalCachedMap");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;Lorg/redisson/api/LocalCachedMapOptions;)Lorg/redisson/api/RLocalCachedMap;");
        methodSignatures.add("getMap");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RMap;");
        methodSignatures.add("getMap");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/api/MapOptions;)Lorg/redisson/api/RMap;");
        methodSignatures.add("getMap");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RMap;");
        methodSignatures.add("getMap");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;Lorg/redisson/api/MapOptions;)Lorg/redisson/api/RMap;");
        methodSignatures.add("getSetMultimap");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RSetMultimap;");
        methodSignatures.add("getSetMultimap");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RSetMultimap;");
        methodSignatures.add("getSetMultimapCache");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RSetMultimapCache;");
        methodSignatures.add("getSetMultimapCache");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RSetMultimapCache;");
        methodSignatures.add("getSemaphore");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RSemaphore;");
        methodSignatures.add("getPermitExpirableSemaphore");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RPermitExpirableSemaphore;");
        methodSignatures.add("getLock");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RLock;");
        methodSignatures.add("getSpinLock");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RLock;");
        methodSignatures.add("getSpinLock");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/api/LockOptions$BackOff;)Lorg/redisson/api/RLock;");
        methodSignatures.add("getFencedLock");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RFencedLock;");
        methodSignatures.add("getFairLock");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RLock;");
        methodSignatures.add("getReadWriteLock");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RReadWriteLock;");
        methodSignatures.add("getSet");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RSet;");
        methodSignatures.add("getSet");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RSet;");
        methodSignatures.add("getSortedSet");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RSortedSet;");
        methodSignatures.add("getSortedSet");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RSortedSet;");
        methodSignatures.add("getScoredSortedSet");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RScoredSortedSet;");
        methodSignatures.add("getScoredSortedSet");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RScoredSortedSet;");
        methodSignatures.add("getLexSortedSet");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RLexSortedSet;");
        methodSignatures.add("getShardedTopic");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RShardedTopic;");
        methodSignatures.add("getShardedTopic");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RShardedTopic;");
        methodSignatures.add("getTopic");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RTopic;");
        methodSignatures.add("getTopic");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RTopic;");
        methodSignatures.add("getReliableTopic");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RReliableTopic;");
        methodSignatures.add("getReliableTopic");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RReliableTopic;");
        methodSignatures.add("getQueue");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RQueue;");
        methodSignatures.add("getTransferQueue");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RTransferQueue;");
        methodSignatures.add("getTransferQueue");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RTransferQueue;");
        methodSignatures.add("getQueue");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RQueue;");
        methodSignatures.add("getRingBuffer");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RRingBuffer;");
        methodSignatures.add("getRingBuffer");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RRingBuffer;");
        methodSignatures.add("getPriorityQueue");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RPriorityQueue;");
        methodSignatures.add("getPriorityQueue");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RPriorityQueue;");
        methodSignatures.add("getPriorityBlockingQueue");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RPriorityBlockingQueue;");
        methodSignatures.add("getPriorityBlockingQueue");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RPriorityBlockingQueue;");
        methodSignatures.add("getPriorityBlockingDeque");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RPriorityBlockingDeque;");
        methodSignatures.add("getPriorityBlockingDeque");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RPriorityBlockingDeque;");
        methodSignatures.add("getPriorityDeque");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RPriorityDeque;");
        methodSignatures.add("getPriorityDeque");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RPriorityDeque;");
        methodSignatures.add("getBlockingQueue");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RBlockingQueue;");
        methodSignatures.add("getBlockingQueue");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RBlockingQueue;");
        methodSignatures.add("getBoundedBlockingQueue");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RBoundedBlockingQueue;");
        methodSignatures.add("getBoundedBlockingQueue");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RBoundedBlockingQueue;");
        methodSignatures.add("getDeque");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RDeque;");
        methodSignatures.add("getDeque");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RDeque;");
        methodSignatures.add("getBlockingDeque");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RBlockingDeque;");
        methodSignatures.add("getBlockingDeque");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RBlockingDeque;");
        methodSignatures.add("getAtomicLong");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RAtomicLong;");
        methodSignatures.add("getAtomicDouble");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RAtomicDouble;");
        methodSignatures.add("getLongAdder");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RLongAdder;");
        methodSignatures.add("getDoubleAdder");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RDoubleAdder;");
        methodSignatures.add("getCountDownLatch");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RCountDownLatch;");
        methodSignatures.add("getBitSet");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RBitSet;");
        methodSignatures.add("getBloomFilter");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RBloomFilter;");
        methodSignatures.add("getBloomFilter");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RBloomFilter;");
        methodSignatures.add("getIdGenerator");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RIdGenerator;");
        methodSignatures.add("getExecutorService");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RScheduledExecutorService;");
        methodSignatures.add("getExecutorService");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/api/ExecutorOptions;)Lorg/redisson/api/RScheduledExecutorService;");
        methodSignatures.add("getExecutorService");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RScheduledExecutorService;");
        methodSignatures.add("getExecutorService");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;Lorg/redisson/api/ExecutorOptions;)Lorg/redisson/api/RScheduledExecutorService;");
        methodSignatures.add("getRemoteService");methodSignatures.add( "(Ljava/lang/String;)Lorg/redisson/api/RRemoteService;");
        methodSignatures.add("getRemoteService");methodSignatures.add( "(Ljava/lang/String;Lorg/redisson/client/codec/Codec;)Lorg/redisson/api/RRemoteService;");
    }

}

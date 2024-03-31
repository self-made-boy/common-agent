package com.github.selfmadeboy.agent.redis;

public  class AssistantRedisSerializer implements org.springframework.data.redis.serializer.RedisSerializer {

    private org.springframework.data.redis.serializer.RedisSerializer serializer;

    private String prefix;

    public static AssistantRedisSerializer instance(org.springframework.data.redis.serializer.RedisSerializer serializer, String prefix){
        return new AssistantRedisSerializer(serializer,prefix);
    }

    public AssistantRedisSerializer(org.springframework.data.redis.serializer.RedisSerializer serializer, String prefix) {
        this.serializer = serializer;
        this.prefix = prefix+":";
    }


    @Override
    public byte[] serialize(Object k) throws org.springframework.data.redis.serializer.SerializationException {
        byte[] bytes;
        if (serializer == null && k instanceof byte[]) {
            bytes = (byte[]) k;
        } else {
            bytes = this.serializer.serialize(k);
        }
        String s = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        return (prefix + s).getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }


    @Override
    public Object deserialize(byte[] bytes) throws org.springframework.data.redis.serializer.SerializationException {
        String s = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);

        if (s.startsWith(prefix)) {
            bytes = s.substring(prefix.length()).getBytes(java.nio.charset.StandardCharsets.UTF_8);
        }
        return serializer != null ?  serializer.deserialize(bytes) :  bytes;
    }
}

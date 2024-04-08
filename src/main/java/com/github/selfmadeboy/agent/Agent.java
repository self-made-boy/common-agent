package com.github.selfmadeboy.agent;


import com.github.selfmadeboy.agent.kafka.KafkaClassFileTransformer;
import com.github.selfmadeboy.agent.redis.RedisClassFileTransformer;
import com.github.selfmadeboy.agent.xxljob.XxlJobClassFileTransformer;


import java.lang.instrument.Instrumentation;
import org.tinylog.Logger;

public class Agent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Logger.info("agent premain start...");
        instrumentation.addTransformer(new KafkaClassFileTransformer(),true);
        instrumentation.addTransformer(new RedisClassFileTransformer(),true);
        instrumentation.addTransformer(new XxlJobClassFileTransformer(),true);
        Logger.info("agent premain end");
    }



}




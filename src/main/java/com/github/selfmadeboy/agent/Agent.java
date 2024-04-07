package com.github.selfmadeboy.agent;

import com.github.selfmadeboy.agent.kafka.KafkaClassFileTransformer;
import com.github.selfmadeboy.agent.redis.RedisClassFileTransformer;
import com.github.selfmadeboy.agent.xxljob.XxlJobClassFileTransformer;
import org.tinylog.Logger;


import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;


public class Agent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Logger.info("agent start...");
        instrumentation.addTransformer(new KafkaClassFileTransformer());
        instrumentation.addTransformer(new RedisClassFileTransformer());
        instrumentation.addTransformer(new XxlJobClassFileTransformer());

    }



}




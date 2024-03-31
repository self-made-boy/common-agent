package com.github.selfmadeboy.agent;

import com.github.selfmadeboy.agent.kafka.KafkaClassFileTransformer;
import com.github.selfmadeboy.agent.redis.RedisClassFileTransformer;
import com.github.selfmadeboy.agent.xxljob.XxlJobClassFileTransformer;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

@Slf4j
public class Agent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        log.info("agent start...");
        instrumentation.addTransformer(new KafkaClassFileTransformer());
        instrumentation.addTransformer(new RedisClassFileTransformer());
        instrumentation.addTransformer(new XxlJobClassFileTransformer());
    }



}




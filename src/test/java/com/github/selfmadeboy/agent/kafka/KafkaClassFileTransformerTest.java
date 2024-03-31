package com.github.selfmadeboy.agent.kafka;

import com.github.selfmadeboy.agent.TransformUtils;
import javassist.CannotCompileException;
import javassist.CtClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

public class KafkaClassFileTransformerTest {

    KafkaClassFileTransformer transformer = new KafkaClassFileTransformer();


    @Test
    public void transformProducer() throws CannotCompileException, IOException {
        Optional<CtClass> optional = TransformUtils.getTargetClass("org.apache.kafka.clients.producer.KafkaProducer", Constants.PRODUCER_QUALIFIED_NAME);
        if (!optional.isPresent()) {
            return;
        }

        CtClass ctClass = transformer.transformProducer(optional.get());

        ctClass.writeFile("Producer.class");
    }

    @Test
    public void transformMessageListener() throws CannotCompileException, IOException {
        Optional<CtClass> optional = TransformUtils.getTargetClass("com.github.selfmadeboy.agent.kafka.MessageListenerTest", Constants.MESSAGE_LISTENER_QUALIFIED_NAME);
        if (!optional.isPresent()) {
            return;
        }

        CtClass ctClass = transformer.transformMessageListener(optional.get());

        ctClass.writeFile("MessageListener.class");
    }

    @Test
    public void transformBatchMessageListener() throws CannotCompileException, IOException {

        Optional<CtClass> optional = TransformUtils.getTargetClass("com.github.selfmadeboy.agent.kafka.BatchMessageListenerTest", Constants.BATCH_MESSAGE_LISTENER_QUALIFIED_NAME);
        if (!optional.isPresent()) {
            return;
        }

        CtClass ctClass = transformer.transformBatchMessageListener(optional.get());

        ctClass.writeFile("BatchMessageListener.class");
    }
}
package com.github.selfmadeboy.agent.kafka;

import org.tinylog.Logger;
import com.github.selfmadeboy.agent.TransformUtils;

import javassist.*;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Optional;

public class KafkaClassFileTransformer implements ClassFileTransformer {



    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        TransformUtils.addClassLoader(loader);
        if (!TransformUtils.hasConfigByKey(Constants.KAFKA_ENV_KEY_NAME)){
            Logger.info("agent skip KafkaClassFileTransformer due not config {}", Constants.KAFKA_ENV_KEY_NAME);
            return classfileBuffer;
        }
        try {
            Optional<CtClass> optional = TransformUtils.getTargetClass(className, Constants.MESSAGE_LISTENER_QUALIFIED_NAME);
            if (optional.isPresent()) {
                return transformMessageListener(optional.get()).toBytecode();
            }

            optional = TransformUtils.getTargetClass(className, Constants.BATCH_MESSAGE_LISTENER_QUALIFIED_NAME);

            if (optional.isPresent()) {
                return transformBatchMessageListener(optional.get()).toBytecode();
            }

            optional = TransformUtils.getTargetClass(className, Constants.PRODUCER_QUALIFIED_NAME);

            if (optional.isPresent()) {
                return transformProducer(optional.get()).toBytecode();
            }

            optional = TransformUtils.getTargetClass(className, Constants.GROUP_QUALIFIED_NAME);

            if (optional.isPresent()) {
                return transformGroup(optional.get()).toBytecode();
            }


        } catch (Exception e) {
            Logger.error(e,"agent transform failed: ");
        }

        return classfileBuffer;
    }

    private boolean isDefault() {
        return "default".equalsIgnoreCase(TransformUtils.getConfigByKey(Constants.KAFKA_ENV_KEY_NAME));
    }

    public CtClass transformGroup(CtClass ctClass) throws CannotCompileException {
        boolean hasEnv = TransformUtils.hasConfigByKey(Constants.KAFKA_ENV_KEY_NAME);

        if (!hasEnv) {
            return ctClass;
        }

        if (isDefault()) {
            return ctClass;
        }

        String env = TransformUtils.getConfigByKey(Constants.KAFKA_ENV_KEY_NAME);

        try {
            CtMethod method = ctClass.getMethod(Constants.GROUP_METHOD_NAME, Constants.GROUP_ID_METHOD_SIGNATURE);
            if (ctClass.equals(method.getDeclaringClass()) && !method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", ctClass.getName(), Constants.GROUP_METHOD_NAME, Constants.GROUP_ID_METHOD_SIGNATURE);

                method.insertAfter(String.format("$_ = $_ + \"%s\";", "-" + env), true);
            }
        } catch (NotFoundException e) {

        }


        return ctClass;
    }
    public CtClass transformProducer(CtClass ctClass) throws CannotCompileException {
        boolean hasEnv = TransformUtils.hasConfigByKey(Constants.KAFKA_ENV_KEY_NAME);

        if (!hasEnv) {
            return ctClass;
        }
        if (isDefault()) {
            return ctClass;
        }


        String env = TransformUtils.getConfigByKey(Constants.KAFKA_ENV_KEY_NAME);
        String headerKey = TransformUtils.getConfigByKey(Constants.KAFKA_HEADER_KEY_NAME);
        if (headerKey == null || headerKey.isEmpty()) {
            return ctClass;
        }

        try {
            CtMethod method = ctClass.getMethod(Constants.PRODUCER_METHOD_NAME, Constants.PRODUCER_METHOD_SIGNATURE_1);
            if (!method.isEmpty()){
                Logger.info("agent enhance class {}#{}{}", ctClass.getName(), Constants.PRODUCER_METHOD_NAME, Constants.PRODUCER_METHOD_SIGNATURE_1);
                method.insertBefore(
                        String.format("if($1.headers().lastHeader(\"%s\") == null){$1.headers().add(\"%s\",\"%s\".getBytes(java.nio.charset.StandardCharsets.UTF_8));}",headerKey,headerKey,env)
                );
            }
        } catch (NotFoundException e) {

        }

        try {
            CtMethod method = ctClass.getMethod(Constants.PRODUCER_METHOD_NAME, Constants.PRODUCER_METHOD_SIGNATURE_2);
            if (!method.isEmpty()){
                Logger.info("agent enhance class {}#{}{}", ctClass.getName(), Constants.PRODUCER_METHOD_NAME, Constants.PRODUCER_METHOD_SIGNATURE_2);
                method.insertBefore(
                        String.format("if($1.headers().lastHeader(\"%s\") == null){$1.headers().add(\"%s\",\"%s\".getBytes(java.nio.charset.StandardCharsets.UTF_8));}",headerKey,headerKey,env)
                );
            }
        } catch (NotFoundException e) {

        }
        return ctClass;
    }

    public CtClass transformMessageListener(CtClass messageListenerClass) throws CannotCompileException {

        boolean hasEnv = TransformUtils.hasConfigByKey(Constants.KAFKA_ENV_KEY_NAME);

        if (!hasEnv) {
            return messageListenerClass;
        }

        try {
            CtMethod method = messageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_1);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", messageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_1);
                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filter($1,null,null) == null){return;}"
                );
            }

        } catch (NotFoundException e) {
            // do nothing
        }

        try {
            CtMethod method = messageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_2);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", messageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_2);

                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filter($1,$2,null) == null){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }

        try {
            CtMethod method = messageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_3);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", messageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_3);
                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filter($1,null,$2) == null){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }

        try {
            CtMethod method = messageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_4);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", messageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.MESSAGE_LISTENER_METHOD_SIGNATURE_4);

                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filter($1,$2,$3) == null){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }
        return messageListenerClass;
    }

    public CtClass transformBatchMessageListener(CtClass batchMessageListenerClass) throws CannotCompileException {

        boolean hasEnv = TransformUtils.hasConfigByKey(Constants.KAFKA_ENV_KEY_NAME);

        if (!hasEnv) {
            return batchMessageListenerClass;
        }

        try {
            CtMethod method = batchMessageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_1);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", batchMessageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_1);

                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filterList($1,null,null).isEmpty()){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }

        try {
            CtMethod method = batchMessageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_2);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", batchMessageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_2);

                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filterList($1,$2,null).isEmpty()){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }

        try {
            CtMethod method = batchMessageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_3);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", batchMessageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_3);

                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filterList($1,null,$2).isEmpty()){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }

        try {
            CtMethod method = batchMessageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_4);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", batchMessageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_4);

                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filterList($1,$2,$3).isEmpty()){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }

        try {
            CtMethod method = batchMessageListenerClass.getMethod(Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_5);
            if (!method.isEmpty()) {
                Logger.info("agent enhance class {}#{}{}", batchMessageListenerClass.getName(), Constants.MESSAGE_LISTENER_METHOD_NAME, Constants.BATCH_MESSAGE_LISTENER_METHOD_SIGNATURE_5);

                method.insertBefore(
                        "if (com.github.selfmadeboy.agent.kafka.Assistant.filterRecords($1,$2,$3) == null){return;}"
                );
            }
        } catch (NotFoundException e) {
            // do nothing
        }

        return batchMessageListenerClass;
    }
}

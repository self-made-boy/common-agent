package com.github.selfmadeboy.agent.redis;


import com.github.selfmadeboy.agent.TransformUtils;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class RedisClassFileTransformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!TransformUtils.hasConfigByKey(Constants.REDIS_ENV_KEY_NAME)) {
            return classfileBuffer;
        }
        try {
            Optional<CtClass> optional = TransformUtils.getTargetClass(className, Constants.REDIS_TEMPLATE_CLASS_NAME);
            if (optional.isPresent()) {
                return transformRedisTemplate(optional.get()).toBytecode();
            }

            Optional<CtClass> optional1 = TransformUtils.getTargetClass(className, Constants.REDISSON_CLIENT_CLASS_NAME);
            if (optional1.isPresent()) {
                return transformRedissonClient(optional1.get()).toBytecode();
            }


        } catch (Exception e) {
            log.error("agent redis transform failed: {} ", className, e);
        }

        return classfileBuffer;
    }

    public CtClass transformRedissonClient(CtClass ctClass) throws CannotCompileException {
        String config = TransformUtils.getConfigByKey(Constants.REDIS_ENV_KEY_NAME);
        for (int i = 0; i < Constants.methodSignatures.size(); i += 2) {
            String k = Constants.methodSignatures.get(i);
            String v = Constants.methodSignatures.get(i + 1);
            try {
                CtMethod method = ctClass.getMethod(k, v);
                if (!method.isEmpty()) {
                    method.insertBefore(String.format("$1=\"%s:\"+$1;", config));
                    log.info("agent enhance class {}#{}{}", ctClass.getName(), k, v);
                }
            } catch (NotFoundException e) {
                log.warn("agent redis not found:{}#{}{}", ctClass.getName(), k, v);
            }
        }


        return ctClass;


    }

    public CtClass transformRedisTemplate(CtClass ctClass) throws CannotCompileException {

        String config = TransformUtils.getConfigByKey(Constants.REDIS_ENV_KEY_NAME);
        try {
            CtMethod method = ctClass.getMethod(Constants.SET_KEY_SERIALIZER_METHOD_NAME, Constants.SET_KEY_SERIALIZER_METHOD_SIGNATURE);
            if (Objects.equals(method.getDeclaringClass(), ctClass)) {
                method.insertBefore(String.format("if (!($1 instanceof com.github.selfmadeboy.agent.redis.AssistantRedisSerializer)){this.keySerializer = com.github.selfmadeboy.agent.redis.AssistantRedisSerializer.instance($1,\"%s\");return;}", config));
                log.info("agent enhance class:{}#{}{}", ctClass.getName(), Constants.SET_KEY_SERIALIZER_METHOD_NAME, Constants.SET_KEY_SERIALIZER_METHOD_SIGNATURE);
            }


        } catch (NotFoundException e) {
            log.warn("agent redis not found:{}#{}{}", ctClass.getName(), Constants.SET_KEY_SERIALIZER_METHOD_NAME, Constants.SET_KEY_SERIALIZER_METHOD_SIGNATURE);
        }

        try {
            CtMethod method = ctClass.getMethod(Constants.AFTER_PROPERTIES_SET_METHOD_NAME, Constants.AFTER_PROPERTIES_SET_METHOD_SIGNATURE);
            if (Objects.equals(method.getDeclaringClass(), ctClass)) {
                method.insertAfter(String.format("if (!(this.keySerializer instanceof com.github.selfmadeboy.agent.redis.AssistantRedisSerializer)){ this.keySerializer = com.github.selfmadeboy.agent.redis.AssistantRedisSerializer.instance(this.keySerializer,\"%s\");}", config));
                log.info("agent enhance class:{}#{}{}", ctClass.getName(), Constants.AFTER_PROPERTIES_SET_METHOD_NAME, Constants.AFTER_PROPERTIES_SET_METHOD_SIGNATURE);
            }
        } catch (NotFoundException e) {
            log.warn("agent redis not found:{}#{}{}", ctClass.getName(), Constants.AFTER_PROPERTIES_SET_METHOD_NAME, Constants.AFTER_PROPERTIES_SET_METHOD_SIGNATURE);
        }


        return ctClass;
    }

}

package com.github.selfmadeboy.agent.redis;


import com.github.selfmadeboy.agent.TransformUtils;

import org.tinylog.Logger;
import javassist.*;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import java.util.Objects;
import java.util.Optional;


public class RedisClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//        TransformUtils.addClassLoader(loader);

        if (!TransformUtils.hasConfigByKey(Constants.REDIS_ENV_KEY_NAME)) {
            return classfileBuffer;
        }
        try {
            Optional<CtClass> optional = TransformUtils.getTargetClass(className, Constants.REDIS_TEMPLATE_CLASS_NAME);
            if (optional.isPresent()) {
                return transformRedisTemplate(loader,optional.get()).toBytecode();
            }

            Optional<CtClass> optional1 = TransformUtils.getTargetClass(className, Constants.REDISSON_CLIENT_CLASS_NAME);
            if (optional1.isPresent()) {
                return transformRedissonClient(optional1.get()).toBytecode();
            }


        } catch (Exception e) {
            Logger.error(e,"agent redis transform failed: {} ", className);
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
                    Logger.info("agent enhance class {}#{}{}", ctClass.getName(), k, v);
                }
            } catch (NotFoundException e) {
                Logger.warn("agent redis not found:{}#{}{}", ctClass.getName(), k, v);
            }
        }


        return ctClass;


    }

    public CtClass transformRedisTemplate(CtClass ctClass) throws CannotCompileException {
        return transformRedisTemplate(null, ctClass);
    }

    public CtClass transformRedisTemplate(ClassLoader loader, CtClass ctClass) throws CannotCompileException {

        String config = TransformUtils.getConfigByKey(Constants.REDIS_ENV_KEY_NAME);
        try {
            System.out.println();
            CtMethod method = ctClass.getMethod(Constants.SET_KEY_SERIALIZER_METHOD_NAME, Constants.SET_KEY_SERIALIZER_METHOD_SIGNATURE);
            if (Objects.equals(method.getDeclaringClass(), ctClass)) {
                method.insertBefore(String.format("try {Class aClass = Class.forName(\"%s\");if (!aClass.isInstance($1)){this.keySerializer = com.github.selfmadeboy.agent.redis.AssistantRedisSerializer.instance($1,\"%s\");return;}} catch (Exception e) {throw new RuntimeException(e);}",Constants.ASSIST_CLASS_NAME, config));
                Logger.info("agent enhance class:{}#{}{}", ctClass.getName(), Constants.SET_KEY_SERIALIZER_METHOD_NAME, Constants.SET_KEY_SERIALIZER_METHOD_SIGNATURE);
            }

            System.out.println();

        } catch (NotFoundException e) {
            Logger.warn("agent redis not found:{}#{}{}", ctClass.getName(), Constants.SET_KEY_SERIALIZER_METHOD_NAME, Constants.SET_KEY_SERIALIZER_METHOD_SIGNATURE);
        }

        try {
            CtMethod method = ctClass.getMethod(Constants.AFTER_PROPERTIES_SET_METHOD_NAME, Constants.AFTER_PROPERTIES_SET_METHOD_SIGNATURE);
            if (Objects.equals(method.getDeclaringClass(), ctClass)) {
//                method.insertAfter(String.format("if (!(this.keySerializer instanceof com.github.selfmadeboy.agent.redis.AssistantRedisSerializer)){ this.keySerializer = com.github.selfmadeboy.agent.redis.AssistantRedisSerializer.instance(this.keySerializer,\"%s\");}", config));
                method.insertAfter(String.format("try {Class aClass = Class.forName(\"%s\");if (!aClass.isInstance(this.keySerializer)){this.keySerializer = com.github.selfmadeboy.agent.redis.AssistantRedisSerializer.instance(this.keySerializer,\"%s\");}} catch (Exception e) {throw new RuntimeException(e);}",Constants.ASSIST_CLASS_NAME, config));

                Logger.info("agent enhance class:{}#{}{}", ctClass.getName(), Constants.AFTER_PROPERTIES_SET_METHOD_NAME, Constants.AFTER_PROPERTIES_SET_METHOD_SIGNATURE);

            }
        } catch (NotFoundException e) {
            Logger.warn("agent redis not found:{}#{}{}", ctClass.getName(), Constants.AFTER_PROPERTIES_SET_METHOD_NAME, Constants.AFTER_PROPERTIES_SET_METHOD_SIGNATURE);
        }

        if (loader != null) {
            TransformUtils.addClassLoader(loader, AssistantRedisSerializer.class);
        }

        return ctClass;
    }

}

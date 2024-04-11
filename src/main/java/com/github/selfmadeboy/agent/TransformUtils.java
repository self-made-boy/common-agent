package com.github.selfmadeboy.agent;

import javassist.*;
import org.tinylog.Logger;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


public class TransformUtils {


    private static AtomicBoolean addLaunchedUrlClassLoader = new AtomicBoolean(false);

    public static void addClassLoader(ClassLoader classLoader) {

        if (Constants.Launched_URL_ClassLoader_ClassName.equals(classLoader.getClass().getName())){
            boolean b = addLaunchedUrlClassLoader.compareAndSet(false, true);
            if (b){
                ClassPool.getDefault().appendClassPath(new LoaderClassPath(classLoader));
                Logger.info("agent ClassPool add LaunchedUrlClassLoader");

                try {
                    CtClass ctClass = ClassPool.getDefault().get("com.github.selfmadeboy.agent.redis.AssistantRedisSerializer");
                    ctClass.toClass(classLoader,null);
                    Logger.info("agent classloader load class: com.github.selfmadeboy.agent.redis.AssistantRedisSerializer");

                } catch (Exception e) {

                }

                try {
                    CtClass ctClass1 = ClassPool.getDefault().get("com.github.selfmadeboy.agent.kafka.Assistant");
                    ctClass1.toClass(classLoader,null);
                    Logger.info("agent classloader load class: com.github.selfmadeboy.agent.kafka.Assistant");
                } catch (Exception e) {

                }


            }
        }

    }

    public static boolean instance(CtClass target, CtClass parent) {
        if (target == null || parent == null) {
            return false;
        }
        if (Objects.equals(target.getName(), parent.getName())) {
            return true;
        }
        if (target.subclassOf(parent)) {
            return true;
        }

        CtClass[] interfaces;
        try {
            interfaces = target.getInterfaces();
        } catch (NotFoundException e) {
            return false;
        }

        if (interfaces != null) {
            for (CtClass item : interfaces) {
                if (instance(item, parent)) {
                    return true;
                }
            }
        }

        return false;

    }

    public static boolean instance(CtClass target, String fullQualifiedClassName) {
        if (target == null || fullQualifiedClassName == null || fullQualifiedClassName.trim().isEmpty()) {
            return false;
        }

        ClassPool pool = ClassPool.getDefault();
        CtClass inCt = null;
        try {
            inCt = pool.get(fullQualifiedClassName);
        } catch (NotFoundException e) {
            return false;
        }
        return instance(target, inCt);

    }

    public static boolean isSystemClass(String className) {
        if (className == null
                || className.startsWith("java")
                || className.startsWith("sun")
                || className.startsWith("jdk")
        ) {
            return true;
        } else {
            return false;
        }
    }


    public static Optional<CtClass> getTargetClass(ClassPool pool, String className, String targetClassName) {
        boolean isSystemClass = TransformUtils.isSystemClass(className);
        if (isSystemClass) {
            return Optional.empty();
        }

        if (pool == null) {
            pool = ClassPool.getDefault();
        }

        try {
            String fullQualifiedClassName = className.replaceAll(Constants.SLASH, Constants.DOT);
            CtClass ctClass = pool.get(fullQualifiedClassName);
            boolean instance = TransformUtils.instance(ctClass, targetClassName);
            if (!instance) {
                return Optional.empty();
            }
            return Optional.of(ctClass);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }

    public static Optional<CtClass> getTargetClass(String className, String targetClassName) {
        return getTargetClass(null, className, targetClassName);

    }


    public static boolean hasConfigByKey(String key) {
        String value = TransformUtils.getConfigByKey(key);
        return value != null && !value.isEmpty();
    }


    public static String getConfigByKey(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isEmpty()) {
            value = System.getenv(key);
        }
        return value;
    }
}

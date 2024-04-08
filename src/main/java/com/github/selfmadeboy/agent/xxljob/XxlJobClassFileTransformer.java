package com.github.selfmadeboy.agent.xxljob;


import com.github.selfmadeboy.agent.TransformUtils;
import org.tinylog.Logger;
import javassist.*;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Objects;
import java.util.Optional;


public class XxlJobClassFileTransformer implements ClassFileTransformer {


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        TransformUtils.addClassLoader(loader);

        String configEnv = TransformUtils.getConfigByKey(Constants.XXLJOB_ENV_KEY_NAME);
        if (  configEnv == null || configEnv.isEmpty()){
           return classfileBuffer;
        }
        Optional<CtClass> optional = TransformUtils.getTargetClass(className, Constants.CLASS_NAME);
        if (optional.isPresent()){

            CtClass ctClass = optional.get();
            try {
                CtMethod method = ctClass.getMethod(Constants.METHOD_NAME, Constants.METHOD_SIGNATURE);
                if (Objects.equals(method.getDeclaringClass(), ctClass)) {
                    method.insertBefore(String.format("if ($1!=null && !$1.isEmpty()){$1=$1 + \"-%s\";}",configEnv));
                    Logger.info("agent enhance {}#{}{}", Constants.CLASS_NAME, Constants.METHOD_NAME, Constants.METHOD_SIGNATURE);
                }
                return ctClass.toBytecode();
            } catch (NotFoundException e) {
                Logger.warn("agent not found target method: {}#{}{}", Constants.CLASS_NAME, Constants.METHOD_NAME, Constants.METHOD_SIGNATURE);
            } catch (Exception e) {
                Logger.error(e,"agent xxl failed");
            }

        }

        return classfileBuffer;

    }



}

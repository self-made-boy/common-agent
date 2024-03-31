package com.github.selfmadeboy.agent;

import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.*;

public class TransformUtilsTest {


    @Test
    public void tttt() {

        Properties properties = System.getProperties();
        System.out.println(properties);
        String kafka = System.getenv("kafka");
        System.out.println(kafka);

        String kakfa = System.getProperty("kafka");
        System.out.println(kakfa);
    }
    @Test
    public void isSystemClass() {


        boolean systemClass = TransformUtils.isSystemClass("java");
        assertEquals(systemClass,true);
    }

    @Test
    public void getTargetClass() throws CannotCompileException, IOException, NotFoundException {

        Optional<CtClass> optional = TransformUtils.getTargetClass("com/github/selfmadeboy/agent/TestMessage", "com.github.selfmadeboy.agent.TestMessage");
        if (optional.isPresent()){
            CtClass ctClass = optional.get();


            CtMethod method1 = ctClass.getMethod("onMessage", "(Ljava/lang/String;)V");
            method1.insertBefore("System.out.println($1);");
            method1.insertBefore("System.out.println(this.a);");


            CtMethod[] methods = ctClass.getMethods();
            CtMethod ctMethod = CtMethod.make(
                    "public boolean _hasEnv() {\n" +
                            "        return true;\n" +
                            "    }",ctClass);
            ctClass.addMethod(ctMethod);
            for (CtMethod method : methods) {
                String signature = method.getSignature();
                String name = method.getName();
                String longName = method.getLongName();
                System.out.println(name+"  "+signature+"  "+longName);
                if (name.equals("onMessage")){

                    method.insertBefore("_hasEnv();");
                }
            }



            ctClass.writeFile("output.class");
        }
    }


}
package com.github.selfmadeboy.agent.redis;

import com.github.selfmadeboy.agent.TransformUtils;
import javassist.CannotCompileException;
import javassist.CtClass;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Optional;

public class RedisClassFileTransformerTest extends TestCase {
    RedisClassFileTransformer transformer = new RedisClassFileTransformer();
    public void testTransformRedissonClient() throws CannotCompileException, IOException {
        Optional<CtClass> optional = TransformUtils.getTargetClass(Constants.REDIS_TEMPLATE_CLASS_NAME, Constants.REDIS_TEMPLATE_CLASS_NAME);
        if (!optional.isPresent()) {
            return;
        }
        CtClass ctClass = transformer.transformRedisTemplate(optional.get());
        ctClass.writeFile("RedisTemplate.class");

    }

    public void testTransformRedisTemplate() {
    }
}
package com.github.selfmadeboy.agent.xxljob;

import org.junit.Test;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class XxlJobClassFileTransformerTest {

    XxlJobClassFileTransformer transformer = new XxlJobClassFileTransformer();
    @Test
    public void transform() throws IOException, IllegalClassFormatException {

        byte[] bytes = transformer.transform(null, "com/xxl/job/core/executor/XxlJobExecutor", null, null, null);

        Files.write(Paths.get("xxl.class"),bytes);



    }
}
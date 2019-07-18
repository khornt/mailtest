package com.horntvedt.mail.camel.transaltors;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class RegistrereKundeForespoerselTranslatorTest {


    protected static String getMessage(final String fileName) throws IOException {
        File responseMessage = new ClassPathResource(fileName).getFile();
        return FileUtils.readFileToString(responseMessage, "utf-8");
    }


    @Test
    public void testensin() {
        assert true;
    }


}

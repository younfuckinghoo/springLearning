package com.hy.validation;

import com.hy.basejava.util.ClassScanUtil;
import com.hy.basejava.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

@SpringBootApplication
public class ValidationLearningApplication {

    public static void main(String[] args) throws IOException {

        PropertiesUtil.readProperties();
        ClassScanUtil.readClass();
        SpringApplication.run(ValidationLearningApplication.class, args);

    }

}

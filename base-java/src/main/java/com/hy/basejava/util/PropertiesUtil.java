package com.hy.basejava.util;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PropertiesUtil {

    public static void readProperties() throws IOException {

        ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
        // 当有依赖多个jar包，且jar包中包含相同路径文件 则会读取到多个资源
        Enumeration<URL> resources = classLoader.getResources("META-INF/my.properties");
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            InputStream inputStream = url.openStream();
            BufferedReader aa= new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            Properties properties = new Properties();
            properties.load(aa);
            Set<Object> objects = properties.keySet();
            for (Object object : objects) {
                System.out.println(properties.get(object));
            }
        }
    }


}

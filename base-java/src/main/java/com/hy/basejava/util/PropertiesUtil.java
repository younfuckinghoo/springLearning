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

    public static void readClass() throws IOException {
        ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
        // 当有依赖多个jar包，且jar包中包含相同路径文件 则会读取到多个资源
        Enumeration<URL> resources = classLoader.getResources("com/hy/");
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
          
            String protocol = url.getProtocol();
            if("jar".equals(protocol)){
                scanJar(url);
            }else if("file".equals(protocol)){
                String urlPath = url.getPath();
                File file = new File(urlPath.substring(1));
                String file1 = url.getFile();
                String path = URLDecoder.decode(file1, StandardCharsets.UTF_8);
                scanFile(file);
            }


        }
    }

    private static void scanFile(File file) {
        if(!file.exists())
            return;
        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File f:files) {
                scanFile(f);
            }
        }else{
            System.out.println(">>>>>"+file.getPath());
        }


    }

    private static void scanJar(URL url) throws IOException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();

        // 遍历Jar包
        Enumeration entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) entries.nextElement();
            if (jarEntry.isDirectory()) {

            }else{
                String name = jarEntry.getName();
                System.out.println("-------"+name);
            }


        }
    }



    public static void main(String[] args) throws IOException {
        readClass();
    }

}

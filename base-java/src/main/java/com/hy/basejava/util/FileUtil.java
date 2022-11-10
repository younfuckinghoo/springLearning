package com.hy.basejava.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    public static Map<String, File> getAllFile(String basePath) {
        Map<String, File> map = new HashMap<>();
        File baseFile = new File(basePath);
        if (baseFile.isDirectory()) {
            File[] childFiles = baseFile.listFiles();
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    map.putAll(getAllFile(childFile.getAbsolutePath()));
                } else {
                    map.put(childFile.getAbsolutePath(), childFile);
                }
            }
        } else {
            map.put(baseFile.getAbsolutePath(), baseFile);
        }
        return map;
    }

    public static String inputStreamReadFile(File file) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            int available = fileInputStream.available();
            byte[] bytes = new byte[available];
            int read = fileInputStream.read(bytes);
            return new String(bytes, Charset.forName("GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fileInputStream!=null)
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

   /* public String readerReadFile(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        fileReader.
        int available = fileInputStream.available();
        byte[] bytes = new byte[available];
        int read = fileInputStream.read(bytes);
        return new String(bytes);
    }*/

    public static void main(String[] args) {
        Map<String, File> allFile = getAllFile("D:\\noah");
        System.out.println(allFile);
    }

}

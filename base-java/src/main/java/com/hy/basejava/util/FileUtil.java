package com.hy.basejava.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {

    public static Map<String, File> getAllFile(String basePath){
        Map<String, File> map = new HashMap<>();
        File baseFile = new File(basePath);
        if (baseFile.isDirectory()){
            File[] childFiles = baseFile.listFiles();
            for (File childFile : childFiles) {
                if (childFile.isDirectory()){
                    map.putAll(getAllFile(childFile.getAbsolutePath()));
                }else{
                    map.put(childFile.getAbsolutePath(),childFile);
                }
            }
        }else{
            map.put(baseFile.getAbsolutePath(),baseFile);
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String, File> allFile = getAllFile("D:\\noah");
        System.out.println(allFile);
    }

}

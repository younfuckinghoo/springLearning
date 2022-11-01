package com.hy.iolearning.thread;

import com.hy.basejava.util.FileUtil;

import java.io.*;
import java.util.*;

public class FileWatcherThread implements Runnable{

    static String pngPath = "D:\\noah\\ycl\\file\\qrcode";
    static Map<String, byte[]> ALL_FILE_MAP = new HashMap<>();
    @Override
    public void run() {


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("-----------------------------任务开始---------------------------");
                Map<String, File> allFile = FileUtil.getAllFile(pngPath);
                Set<Map.Entry<String, File>> entries = allFile.entrySet();
                for (Map.Entry<String, File> entry : entries) {
                    if (ALL_FILE_MAP.containsKey(entry.getKey())) {
                        boolean b = compareFileContent(entry.getKey(), entry.getValue());
                        if (!b){
                            System.out.println(entry.getKey() + "-----------");
                        }

                    }else{
                        setFileByteArray(entry.getKey(),entry.getValue());
                    }
                }
                System.out.println("-----------------------------任务结束---------------------------");
            }
        };
        timer.schedule(task,0,1000L*60);

    }

    private void setFileByteArray(String key ,File newFile) {
        try {
            FileInputStream newFileInput = new FileInputStream(newFile);
        byte[] newByteBuff = new byte[1024];
        byte[] newBytes = new byte[0];
            int newRead = 0,newLength = 0;
            while ((newRead = newFileInput.read(newByteBuff)) > 0) {
                newLength+=newRead;
                byte[] newBytesNew = new byte[newLength];
                System.arraycopy(newBytes,0,newBytesNew,0,newLength-newRead);
                System.arraycopy(newByteBuff,0,newBytesNew,newLength-newRead,newRead);
                newBytes = newBytesNew;
            }
            ALL_FILE_MAP.put(key,newBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean compareFileContent(String key, File newFile) {
        try {
            byte[] originalBytes = ALL_FILE_MAP.get(key);
            FileInputStream newFileInput = new FileInputStream(newFile);
            byte[] newByteBuff = new byte[1024];
            byte[] newBytes = new byte[0];
            int oldLength = originalBytes.length,newRead = 0,newLength = 0;
            while ((newRead = newFileInput.read(newByteBuff)) > 0) {
                newLength+=newRead;
                byte[] newBytesNew = new byte[newLength];
                System.arraycopy(newBytes,0,newBytesNew,0,newLength-newRead);
                System.arraycopy(newByteBuff,0,newBytesNew,newLength-newRead,newRead);
                newBytes = newBytesNew;
            }
            ALL_FILE_MAP.put(key,newBytes);
            if (newBytes.length==oldLength && Arrays.equals(newBytes,originalBytes)) {
                return true;
            }else {
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

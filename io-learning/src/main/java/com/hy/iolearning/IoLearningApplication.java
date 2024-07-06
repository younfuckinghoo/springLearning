package com.hy.iolearning;

import com.hy.iolearning.thread.FileWatcherThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IoLearningApplication {

    public static void main(String[] args) {
//        FileWatcherThread fileWatcherThread = new FileWatcherThread();
//        Thread thread = new Thread(fileWatcherThread);
//        thread.setDaemon(true);
//        thread.run();
        SpringApplication.run(IoLearningApplication.class, args);
    }

}

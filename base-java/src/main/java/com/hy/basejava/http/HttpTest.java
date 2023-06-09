package com.hy.basejava.http;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class HttpTest {

    public static String sendRequest(String urlParam, String requestType,String jsonBody) {

        HttpURLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try {
            URL url = new URL(urlParam);
            //得到连接对象
            con = (HttpURLConnection) url.openConnection();
            //设置请求类型
            con.setRequestMethod(requestType);
            //允许写出
            con.setDoOutput(true);
            //允许读入
            con.setDoInput(true);
            //不使用缓存
            con.setUseCaches(false);
            //设置请求需要返回的数据类型和字符集类型
            con.setRequestProperty("Content-Type", "application/json;charset=GBK");
            try (PrintWriter writer = new PrintWriter(con.getOutputStream())) {
                writer.write(jsonBody);
                writer.flush();
            }


            //得到响应码
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到响应流
                InputStream inputStream = con.getInputStream();
                //将响应流转换成字符串
                resultBuffer = new StringBuffer();
                String line;
                buffer = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                while ((line = buffer.readLine()) != null) {
                    resultBuffer.append(line);
                }
                return resultBuffer.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {

        String port = "8088";
        if (args.length>0){
            if ("tongWeb".equals(args[0])){
                port = "8088";
            }else {
                port = "8089";
            }
        }
        if ("8088".equals(port)){
            System.out.println("--------------------------正在请求TongWeb----------------------");
        }else{
            System.out.println("--------------------------正在请求Tomcat----------------------");
        }
        String url ="http://127.0.0.1:"+port+"/noahbs/factory/dataManage/queryViews";
        String requestType = "POST";
        String json ="{\"renderPosition\":\"left\"}";
        Logger mylogger = Logger.getLogger("mylogger");
//        System.out.println("------------------------"+sendRequest(url, requestType, json));
        long currentTimeMillis = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
        executorService.execute(new SendRunnable(countDownLatch,url,requestType,json,mylogger));
        }
try {
    countDownLatch.await();
    System.out.println(">>>>>>>>>>>>>>>"+countDownLatch.getCount());
    long spendTime = (System.currentTimeMillis() - currentTimeMillis) / 1000;
    System.out.println("耗时："+spendTime+"秒");
    executorService.shutdown();
} catch (InterruptedException e) {
    e.printStackTrace();
}
    }

    @AllArgsConstructor
    static class SendRunnable implements Runnable{
        CountDownLatch countDownLatch;
        String url ;
        String requestType ;
        String json ;
        Logger mylogger;
        @Override
        public void run() {
            try{
                mylogger.info("----------------"+sendRequest(url, requestType, json));
                countDownLatch.countDown();
                mylogger.info(">>>>>>>>>>>>>>>>>>"+countDownLatch.getCount());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }




}

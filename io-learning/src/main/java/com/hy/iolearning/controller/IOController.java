package com.hy.iolearning.controller;

import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("io")
public class IOController {


    @GetMapping("img")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pngPath = "D:\\noah\\ycl\\file\\qrcode\\toBiliBili.png";
        File file = new File(pngPath);
        if (file.exists()){
            FileInputStream fileOutputStream = new FileInputStream(file);

            byte[] byteArray = new byte[fileOutputStream.available()];
            fileOutputStream.read(byteArray);
            response.setHeader("Access-Control-Allow-Origin","*");
            response.setContentType("image/png;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("bilibili.png", "UTF-8"));
            response.getOutputStream().write(byteArray);
            response.getOutputStream().flush();
        }

    }


}

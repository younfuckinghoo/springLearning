package com.hy.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/login")
public class LoginController {


    public void stimulate(HttpServletResponse response, HttpServletRequest request) throws IOException {

        response.sendRedirect("/");

    }

}

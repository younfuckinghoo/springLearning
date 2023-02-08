package com.hy.validation.entity;

import lombok.Data;

@Data
public class Root {
    public Root(String name, String method, String api,String summary,  String parameters, String responses) {
        this.name = name;
        this.method = method;
        this.api = api;
        this.summary = summary;
        this.parameters = parameters;
        this.responses = responses;
    }

    private String summary;

    private String name;

    private String method;

    private String api;
    private String parameters;
    private String responses;
}


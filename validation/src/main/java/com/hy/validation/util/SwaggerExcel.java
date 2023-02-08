//package com.hy.validation.util;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hy.validation.entity.Root;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//
//public class SwaggerExcel {
//    public static void main(String[] args) throws IOException {
//
//        File file1 = new File("C:\\Users\\Administrstor\\Documents\\AST\\mes\\工艺分解检查\\接口文档\\api-json1.txt");
//        FileInputStream fileInputStream = new FileInputStream(file1);
//        byte[] bytes = fileInputStream.readAllBytes();
//        JsonNode jsonNode = new ObjectMapper().readTree(bytes);
//
//        /**
//         * 取所有数据并存到HashMap中
//         */
//        String api;
//        HashMap<String, List<Root>> hm = new HashMap<>();
//        JsonNode node = jsonNode.findValue("paths");
//        Iterator<String> stringIterator = node.fieldNames();
//        while (stringIterator.hasNext()) {
//            //api
//            JsonNode tags = node.findValue((api = stringIterator.next()));
//            Iterator<String> methodsname = tags.fieldNames();
//            while (methodsname.hasNext()) {
//                //方法
//                String method = methodsname.next();
//                JsonNode methods = tags.findValue(method);
//                String name = methods.findValue("tags").get(0).asText();
//                String summary = methods.findValue("summary").asText();
//                JsonNode parameters1 = methods.findValue("parameters");
//                String parameters = parameters1.toString();
//                JsonNode responses1 = methods.findValue("responses");
//                String responses = responses1.toString();
//                //当前查询到的一个接口数据放到hashmap里管理
//                Root root = new Root(name, method, api,summary,parameters,responses);
//                if (hm.containsKey(root.getName())) {
//                    List<Root> roots = hm.get(root.getName());
//                    roots.add(root);
//                    hm.put(root.getName(), roots);
//                } else {
//                    ArrayList<Root> roots = new ArrayList<>();
//                    roots.add(root);
//                    hm.put(root.getName(), roots);
//                }
//
//            }
//
//        }
//
//        /**
//         * 获得name的顺序，并按顺序写入csv
//         */
//        File file = new File("C:\\Users\\Administrstor\\Documents\\AST\\mes\\工艺分解检查\\接口文档\\result.csv");
//        //excel不能读取utf-8编码的csv文件
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
//                new FileOutputStream(file), "GBK"));
//
//        Iterator<JsonNode> names = jsonNode.findValue("tags").iterator();
//        while (names.hasNext()) {
//            String name = names.next().findValue("name").asText();
//            Iterator<Root> iterator1 = hm.get(name).iterator();
//            bufferedWriter.write(name + ",");
//            Boolean isFirst = true;
//            //如果是第一行增加name，如果不是填入空白格
//            while (iterator1.hasNext()) {
//                if (!isFirst) {
//                    bufferedWriter.write(",");
//                } else {
//                    isFirst = false;
//                }
//                Root next = iterator1.next();
//                bufferedWriter.write(next.getMethod() + "," +
//                        next.getApi() + "," + next.getSummary()+ "," + next.getParameters().replace(",", "，")+ "," + next.getResponses().replace(",", "，"));
//                bufferedWriter.newLine();
//            }
//
//        }
//        bufferedWriter.close();
//        //打开生成的csv文件
//        Runtime.getRuntime().exec("cmd /c start C:\\Users\\Administrstor\\Documents\\AST\\mes\\工艺分解检查\\接口文档\\result.csv");
//        System.out.println("done");
//
//
//    }
//}

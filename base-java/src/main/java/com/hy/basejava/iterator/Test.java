package com.hy.basejava.iterator;

import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
        MyIterable myIterable = new MyIterable(1, 2, 3, 4, 5);
        // 实现了iterable接口可直接用for of循环
        for (String s : myIterable) {
            System.out.println(s);
        }
        System.out.println("------------------------------------------------------");
        Iterator<String> iterator = myIterable.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if ("3".equals(next))
                iterator.remove();
        }
        for (String s : myIterable) {
            System.out.println(s);
        }


    }
}

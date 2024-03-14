package com.hy.basejava.dp;

import cn.hutool.core.lang.tree.Tree;

import java.util.TreeMap;

/**
 * 最大相同子串的动态规划solution
 *  1.当前结果依赖上一个结果，能分解出子结构或方程
 *  2.当前结果无后效性，也就是说当前结果算出来之后后面结果用的时候不会再变
 *  3.子问题重叠
 */
public class DynamicProgramming1 {

    public static void main(String[] args) {
//        testMaxChildStr();
        testMaxChildStr1();
    }

    /**
     * 最大子字符串 个数打印
     */
    public static void testMaxChildStr(){
        String a = "adbchtjkll",b="acctkll";
        char[] aArr = a.toCharArray(),bArr = b.toCharArray();
        int[][] result = new int[aArr.length][bArr.length];

        for (int i = 0; i < aArr.length; i++) {
            for (int j = 0; j < bArr.length; j++) {
                if (aArr[i] == bArr[j]){
                    if(i==0 || j==0) result[i][j] = 1;
                    else result[i][j] = result[i-1][j-1] + 1;
                }else{
                    if(i==0 || j==0) result[i][j] = 0;
                    else result[i][j] = Math.max(result[i-1][j],result[i][j-1]);
                }
            }
        }
        System.out.println(result[aArr.length-1][bArr.length-1]);

    }
    /**
     * 最大子字符串 子序列打印
     */
    public static void testMaxChildStr1(){
        String a = "adbchtjkll",b="acctkll";
        char[] aArr = a.toCharArray(),bArr = b.toCharArray();
        String[][] result = new String[aArr.length][bArr.length];

        for (int i = 0; i < aArr.length; i++) {
            for (int j = 0; j < bArr.length; j++) {
                if (aArr[i] == bArr[j]){
                    if(i==0 || j==0) result[i][j] = String.valueOf(bArr[j]);
                    else result[i][j] = result[i-1][j-1] + bArr[j];
                }else{
                    if(i==0 || j==0){
                        result[i][j] = "";
                    }else {
                        if (result[i-1][j].length()>result[i][j-1].length()) {
                            result[i][j] = result[i-1][j];
                        }else{
                            result[i][j] = result[i][j-1];
                        }
                    }
                }
            }
        }
        System.out.println(result[aArr.length-1][bArr.length-1]);

    }

}

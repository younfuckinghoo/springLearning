package com.hy.basejava.dp;

/**
 * 最长上升子串
 */
public class LongestAscendStr {


    public static void main(String[] args) {

        test1();
    }

    /**
     * 问题分析：
     * 从0到第i个字符的字符串的解依赖前一个
     * f(i) = chars[i]>f(i-1) 放弃
     *
     * 搜索到的思路：
     * 将第i个下标，和子串长度l同时作为变量 构造一个l*i的数组，数组中的内容存当前子串的最大值，最终结果为最深的列中值的数量为最长子串数
     *
     * 构建一个l行 i列的矩阵，
     */
    public static void test1(){
        String s = "xyzasbmnhdue";
        char[] chars = s.toCharArray();
        char[][] charMatrix = new char[chars.length][chars.length];
        System.out.println(charMatrix[0][0]+0 );
        charMatrix[0] = chars;
        // 子串长度 从1到chars.length
        for (int l = 1; l < chars.length; l++) {
            // 下标 到每个下标为止的子串
            for (int i = l; i < chars.length; i++) {
                char x = chars[i];
                // 去上一行找子串长度比自己小的最大字符
                for (int j = 0; j < i; j++) {
                    if (Character.MIN_VALUE!=charMatrix[l-1][j] && x>charMatrix[l-1][j]){
                        charMatrix[l][i] = x;
                    }
                }

            }
        }

        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                System.out.print(String.valueOf(charMatrix[i][j]) + '\t');
            }
            System.out.println();
        }

    }


}

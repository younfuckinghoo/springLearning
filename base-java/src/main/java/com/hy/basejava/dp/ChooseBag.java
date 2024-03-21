package com.hy.basejava.dp;

/**
 * 最长上升子串
 */
public class ChooseBag {


    public static void main(String[] args) {

        test2();
    }


    /**
     * 01背包
     */
    public static void test1(){
        int maxWeight = 20;
        int[] weightArr = new int[]{1,2,20,5,8,4,3};
        int[] valueArr = new int[]{10,3,25,5,8,5,4};

        int[][] result = new int[weightArr.length][maxWeight+1];

        // 将前i件物品装入限重为j的背包内
        for (int i = 0; i < weightArr.length; i++) {
            for (int j = 1; j <= maxWeight; j++) {
                if(i==0){
                    // 如果放入：剩余的承重
                    int lastWeight = maxWeight - weightArr[i];
                    // 能放入
                    if(lastWeight<=maxWeight){
                        // 当前背包承重大于当前物品重量表示可以放入 价值为当前物品的价值
                        if (j >= weightArr[i]){
                            result[i][j] = valueArr[i];
                        }else{
                            //假设背包承重大于剩余承重 代表未放入
                            result[i][j] = 0;
                        }
                    }

                }else{
                    // 如果放上 那么放上后当前背包承重如下
                    int lastWeight = j - weightArr[i];
                    // 能放开
                    if(lastWeight>=0){
                        //当前的最优解            不捡            捡了（要从假设前一件物品剩余了足够的承重这一条件的最高价值的基础上＋当前价值）
                        result[i][j] = Math.max(result[i-1][j],result[i-1][lastWeight]+valueArr[i]);
                    }else{
                        result[i][j] = result[i-1][j];
                    }
                }

            }
        }

        for (int[] ints : result) {
            for (int anInt : ints) {
                System.out.print(anInt+"\t");
            }
            System.out.println();
        }
    }

    /**
     * 01背包空间优化版
     */
    public static void test2(){
        int maxWeight = 20;
        int[] weightArr = new int[]{1,2,20,5,8,4,3};
        int[] valueArr = new int[]{10,3,25,5,8,5,4};

        int[] result = new int[maxWeight+1];

        // 将前i件物品装入限重为j的背包内
        for (int i = 0; i < weightArr.length; i++) {
            for (int j = maxWeight; j > 0; j--) {
                if(i==0){
                    // 如果放入：剩余的承重
                    int lastWeight = maxWeight - weightArr[i];
                    // 能放入
                    if(lastWeight<=maxWeight){
                        // 当前背包承重大于当前物品重量表示可以放入 价值为当前物品的价值
                        if (j >= weightArr[i]){
                            result[j] = valueArr[i];
                        }else{
                            //假设背包承重大于剩余承重 代表未放入
                            result[j] = 0;
                        }
                    }

                }else{
                    // 如果放上 那么放上后当前背包承重如下
                    int lastWeight = j - weightArr[i];
                    // 能放开
                    if(lastWeight>=0){
                        //当前的最优解            不捡            捡了（要从假设前一件物品剩余了足够的承重这一条件的最高价值的基础上＋当前价值）
                        result[j] = Math.max(result[j],result[lastWeight]+valueArr[i]);
                    }else{
                        result[j] = result[j];
                    }
                }

            }
        }
            for (int anInt : result) {
                System.out.print(anInt+"\t");
            }

    }


}

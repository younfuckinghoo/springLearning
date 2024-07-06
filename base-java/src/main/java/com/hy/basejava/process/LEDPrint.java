package com.hy.basejava.process;



import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
* Description: 像led一样打印字符
* @createDate: 2023/6/26 15:31
* @author haoyong
* @lastModifyBy haoyong
*/
public class LEDPrint {



    static class HanZi{

        private int rowCount;
        private int[][] data;

        public HanZi(int rowCount) {
            this.rowCount = rowCount;
        }



        public HanZi initData(int[][] data){
            this.data = data;
            return this;
        }


        public void print() {
            int length = data.length;
            // 一行一行地去打印
            for (int i = 0; i < rowCount ; i++) {
                for (int j = 0; j < length; j++) {
                    int i1 = data[j][i];
                    String format = String.format("%08d", Integer.valueOf(Integer.toBinaryString(i1)));
                    format = format.replace("0","   ");
                    format = format.replace("1"," # ");
                    System.out.print(format);
                }
                System.out.println();
            }

        }
    }

}

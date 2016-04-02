package com.lidnec.infrared;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by lindec on 2016/3/15.
 */
public class FilteringTools {

  

    public static FilteringTools Tool() {
        return new FilteringTools();
    }

    /**
     * 相近数据规整数组
     * 如：range = 100 ，则两数相近在100范围内进行规整 ｛500,504,300｝-> ｛502,502,300｝
     *
     * @param array 数值
     * @param range 比较范围
     * @return 返回规整后的数组
     */
    public int[] adjustAarry(int[] array, int range) {
        int[] adjArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            long sum = array[i];
            int count = 1;
            for (int j = 0; j < array.length; j++) {
                if (i != j) {
                    if (abs(array[i] - array[j]) < range) {
                        sum += array[j];
                        count++;
                    }
                }
            }
            adjArray[i] = (int) (sum / count);
        }
        return adjArray;
    }

    public int[] fixedAarry(int[] array) {
        int[] fixedArray = new int[array.length];
        List<Integer> list = new ArrayList<Integer>();
        list.add(array[0]);
        for (int i = 0; i < array.length; i++) {
            if (!list.contains(array[i])) list.add(array[i]);
        }
        Integer[] ccArray = list.toArray(new Integer[list.size()]);
        Arrays.sort(ccArray);
        Integer[] cArray = new Integer[ccArray.length];
        for (int i = 0; i < ccArray.length; i++) {
            cArray[i] = (ccArray[i] + 50) / 100;
//            ccArray[i] = (ccArray[i] + 50) / 100;
            System.out.println(cArray[i]);
//            System.out.println(ccArray[i]);
        }
        for (int m = 0; m < array.length; m++) {
            for (int n = 0; n < ccArray.length; n++) {
                if (array[m] == ccArray[n]) {
                    fixedArray[m] = n + 1;
                }
            }
        }
        for (int i : ccArray) System.out.println(i);
        for (int i : fixedArray) System.out.println(i);
        int[] Array = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            Array[i] = cArray[fixedArray[i]-1]*100;
//            System.out.println(Array[i]);
        }
//        return fixedArray;
        return Array;
    }


//    public static void main(String[] args) {
//        int[] param = {3742, 1482, 721, 1064, 704, 1059, 704, 335, 700, 335, 700, 335, 700, 1064, 700, 335, 699, 335, 699, 1063, 704, 1063, 700, 335, 700, 1063, 700, 335, 700
//                , 335, 700, 1064, 704, 1059, 704, 335, 700, 1064, 700, 1064, 700, 335, 700, 335, 700, 1063, 704, 330, 704, 330, 704, 1064, 700, 335, 700, 335, 700, 335, 700, 335
//                , 700, 335, 700, 335, 700, 335, 700, 335, 700, 334, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 1063, 700, 335, 700, 335
//                , 699, 1063, 705, 330, 704, 331, 704, 1060, 704, 1064, 700, 335, 700, 335, 700, 339, 700, 335, 700, 334, 700, 335, 700, 1063, 700, 335, 700, 1064, 704, 330, 700
//                , 335, 700, 339, 704, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 1064, 704, 1059, 704, 335, 700, 1063, 700, 1063, 704, 331, 704, 330, 704, 330, 704, 331
//                , 705, 330, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 699, 335, 700, 335, 700, 330
//                , 704, 331, 704, 330, 704, 331, 704, 331, 704, 330, 704, 331, 704, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 1063, 700,
//                335, 700, 335, 700, 1063, 705, 1059, 704, 331, 704, 331, 704, 331, 704, 330, 704, 99860
//        };
//        int[] array = FilteringTools.Tool().adjustAarry(param, 100);
//        FilteringTools.Tool().fixedAarry(array);
////        for (int a : array) {
////            System.out.println(a);
////        }
//
//    }
}

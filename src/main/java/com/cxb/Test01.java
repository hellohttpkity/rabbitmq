package com.cxb;

/**
 * @author: cxb
 * @create: 2022-07-06 22:48
 */
public class Test01 {
    public static void main(String[] args) {
        String s = "adb.txt";
       String s1 =  s.substring(s.lastIndexOf("."));
        System.out.println(s1);
    }
}

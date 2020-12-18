package com.yxy.datastructure.redis.sds;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  2:53 下午
 */
public class Main {

    public static void main(String[] args) {

        Sds sds = SdsManager.getSdsByType(SdsManager.SDS_TYPE_5);
        sds.add("hello");
        StringBuilder str = new StringBuilder("i");
        for (int i = 0; i < 50; i++) {
            str.append("i");
        }
        sds.add(str.toString());
        System.out.println(sds.toString());
        sds.set("1");
        System.out.println(sds.toString());
        for (int i = 0; i < 200; i++) {
            str.append("i");
        }
        sds.add(str.toString());
        System.out.println(sds.toString());
    }
}

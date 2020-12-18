package com.yxy.datastructure.redis.sds;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  2:36 下午
 */
public class Sdshdr5 extends Sds {

    public Sdshdr5(String str) {
        this.buf = new char[32];
        char[] chars = str.toCharArray();
        System.arraycopy(chars, 0, buf, 0, chars.length);
        this.alloc = SdsManager.SDS_TYPE_8_SIZE;
        this.type = SdsManager.SDS_TYPE_5;
    }

    public Sdshdr5() {
        this.buf = new char[32];
        this.alloc = 32;
    }
}

package com.yxy.datastructure.redis.sds;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  3:14 下午
 */
public class Sdshdr16 extends Sds {

    public Sdshdr16() {
        this.buf = new char[128];
        this.alloc = 128;
    }
}

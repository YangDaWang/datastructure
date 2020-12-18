package com.yxy.datastructure.redis.sds;

import lombok.Data;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  2:48 下午
 */
public class Sdshdr8 extends Sds {

    public Sdshdr8() {
        this.buf = new char[64];
        this.alloc = 64;
    }
}

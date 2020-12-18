package com.yxy.datastructure.redis.sds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  3:15 下午
 */
public class Sdshdr64 extends Sds {

    public Sdshdr64() {
        this.buf = new char[512];
        this.alloc = 512;
    }
}

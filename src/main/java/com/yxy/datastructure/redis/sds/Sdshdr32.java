package com.yxy.datastructure.redis.sds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  3:14 下午
 */
public class Sdshdr32 extends Sds {

    public Sdshdr32() {
        this.buf = new char[256];
        this.alloc = 256;
    }
}

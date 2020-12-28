package com.yxy.datastructure.redis.hash;

import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author XinYu Yang
 * @date 2020/12/28  8:09 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ht {

    private volatile int size;
    private RedisHashNode[] table;
    //掩码
    private volatile int sizemask;
    private int used;

}

package com.yxy.datastructure.redis.hash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author XinYu Yang
 * @date 2020/12/28  8:15 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RedisHashNode {

    private String key;
    private Object val;
    private RedisHashNode next;

}

package com.yxy.datastructure.redis.skiplist;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author XinYu Yang
 * @date 2020/12/24  8:29 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SkipListNode {

    private Integer value;
    private List<SkipListNode> nextNode;

    public SkipListNode(Integer value) {
        this.value = value;
        nextNode = new ArrayList<SkipListNode>();
    }
}

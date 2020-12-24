package com.yxy.datastructure.redis.skiplist;

import lombok.Data;

/**
 * @Author XinYu Yang
 * @date 2020/12/24  8:30 下午
 */
@Data
public class SkipList {

    private SkipListNode head;
    private int maxLevel;
    private int size;
    private static final double PROBABILITY = 0.5;

    public SkipList() {
        size = 0;
        maxLevel = 0;
        head = new SkipListNode(null);
        head.setNextNode(null);
    }

    public SkipListNode getHeader() {
        return head;
    }

    private boolean lessThan(Integer a, Integer b) {
        return a.compareTo(b) < 0;
    }

    public void add(Integer newValue) {
        //如果节点不存在，证明是第一个节点
        if (!contains(newValue)) {
            size++;
            int level = 0;
            while (Math.random() < PROBABILITY) {
                level++;
            }
            while (level > maxLevel) {
                head.getNextNode().add(null);
                maxLevel++;
            }
            SkipListNode newNode = new SkipListNode(newValue);
            SkipListNode head = this.head;
            do {
                SkipListNode next = findNext(newValue, head, level);
                //类似链表的操作，把next.next给new node的next
                newNode.getNextNode().add(0, next.getNextNode().get(level));
                //把next set 成new node
                head.getNextNode().set(level, newNode);
            } while (level-- > 0);
        }
    }

    public void delete(Integer delValue) {
        if (contains(delValue)) {
            SkipListNode delNode = find(delValue);
            size--;
            int level = maxLevel;
            SkipListNode node = head;
            do {
                node = findNext(delValue, node, level);
                //找到直接删
                node.getNextNode().set(level, delNode.getNextNode().get(level));
            } while (level-- > 0);
        }
    }

    public boolean contains(Integer value) {
        SkipListNode node = find(value);
        return node != null && node.getValue() != null && node.getValue().equals(value);
    }

    //找到小于入参的最大节点
    private SkipListNode find(Integer e) {
        return find(e, head, maxLevel);
    }

    private SkipListNode find(Integer e, SkipListNode current, int level) {
        do {
            current = findNext(e, current, level);
        } while (level-- > 0);
        return current;
    }

    private SkipListNode findNext(Integer val, SkipListNode node, int level) {
        SkipListNode next = node.getNextNode().get(level);
        while (next != null) {
            Integer value = next.getValue();
            if (lessThan(value, val)) {
                break;
            }
            node = next;
            next = node.getNextNode().get(level);
        }
        return node;
    }


}

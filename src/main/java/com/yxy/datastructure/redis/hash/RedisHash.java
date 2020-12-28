package com.yxy.datastructure.redis.hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author XinYu Yang
 * @date 2020/12/28  8:03 下午
 */
public class RedisHash {

    private int type;
    private int privdata;
    private ArrayList<Ht> ht;
    private int rehashidx;

    /**
     * 初始化
     */
    public RedisHash(int type, int privdata) {
        this.type = type;
        this.privdata = privdata;
        ht = new ArrayList<>();
        ht.add(Ht.builder().size(0).sizemask(3).table(new RedisHashNode[4]).used(0).build());
        ht.add(Ht.builder().size(0).sizemask(3).table(new RedisHashNode[4]).used(0).build());
        this.rehashidx = -1;
    }

    public Object get(String key) {
        //判断是否在rehash
        String node = findNode(this.ht.get(0), key);
        if (stringIsEmpty(node) && this.rehashidx == 0) {
            //正在rehash,需要查两个表
            node = findNode(this.ht.get(1), key);
        }
        return node == null ? "" : node;
    }

    public Boolean stringIsEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private String findNode(Ht ht, String key) {
        int index = key.hashCode() & ht.getSizemask();
        RedisHashNode redisHashNode = ht.getTable()[index];
        if (redisHashNode == null) {
            return "";
        }
        while (redisHashNode != null) {
            if (redisHashNode.getKey().equals(key) && key.hashCode() == redisHashNode.getKey()
                    .hashCode()) {
                return redisHashNode.getVal().toString();
            }
            redisHashNode = redisHashNode.getNext();
        }
        //此处应该抛异常
        return "";
    }

    /**
     * 主要模拟一下扩容的流程
     */
    public int set(String key, Object val) {
        Ht ht = this.ht.get(0);
        RedisHashNode newNode = RedisHashNode.builder().key(key).next(null).val(val).build();
        if (this.rehashidx == 0) {
            //扩容中
            setNode(newNode, this.ht.get(1));
            return 1;
        }
        if (needCapacity(ht)) {
            //需要扩容
            capacity(newNode, ht);
        } else {
            //不需要扩容
            setNode(newNode, ht);
        }
        return 1;
    }

    private void setNode(RedisHashNode newNode, Ht ht) {
        int index = newNode.getKey().hashCode() & ht.getSizemask();
        RedisHashNode redisHashNode = ht.getTable()[index];
        while (redisHashNode.getNext() != null) {
            redisHashNode = redisHashNode.getNext();
        }
        redisHashNode.setNext(newNode);
    }

    private synchronized void capacity(RedisHashNode newNode, Ht ht) {
        //简单防一下并发
        if (rehashidx == 0) {
            setNode(newNode, this.ht.get(1));
            return;
        }
        this.rehashidx = 0;
        int newSize = ht.getSize() * 2;
        Ht ht1 = this.ht.get(1);
        RedisHashNode[]
                table = new RedisHashNode[newSize];
        ht.setSizemask(newSize - 1);
        int index = newNode.getKey().hashCode() & ht.getSizemask();
        RedisHashNode redisHashNode = table[index];
        while (redisHashNode.getNext() != null) {
            redisHashNode = redisHashNode.getNext();
        }
        redisHashNode.setNext(newNode);
        ht1.setTable(table);
        //简单模拟一下异步扩容
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncCapacity();
            }
        }).start();
    }

    //异步扩容
    private void syncCapacity() {
        Ht ht = this.ht.get(0);
        RedisHashNode[] table = ht.getTable();
        for (RedisHashNode node : table) {
            setNode(node, this.ht.get(1));
        }
    }

    private boolean needCapacity(Ht ht) {
        int newSize = ht.getSize() + 1;
        if (newSize > ht.getTable().length) {
            return true;
        }
        return false;
    }

    public int del(Object node) {
        //todo
        return -1;
    }
}

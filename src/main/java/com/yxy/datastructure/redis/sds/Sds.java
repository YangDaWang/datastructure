package com.yxy.datastructure.redis.sds;

import java.util.Arrays;
import lombok.var;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  2:59 下午
 */
public abstract class Sds {

    int len;
    int alloc;
    byte type;
    char[] buf;

    void init(String str) {
        char[] chars = str.toCharArray();
        System.arraycopy(chars, 0, buf, 0, str.length());
        len = str.length();
    }

    void add(String str) {
        int size = str.length() + len;
        if (size > alloc) {
            // 需要扩容
            buf = capacity(size);
            type = SdsManager.getType(size);
        }
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            buf[i + len] = chars[i];
        }
        len = len + chars.length;
    }

    protected char[] capacity(int size) {
        byte type = SdsManager.getType(size);
        char[] tempBuf = SdsManager.getSdsByType(type).buf;
        System.arraycopy(buf, 0, tempBuf, 0, buf.length);
        return tempBuf;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.buf);
    }

    void set(String str) {
        int size = str.length();
        char[] chars = str.toCharArray();
        byte type = SdsManager.getType(size);
        if (type != this.type) {
            char[] tempBuf = SdsManager.getSdsByType(type).buf;
            System.arraycopy(chars, 0, tempBuf, 0, str.length());
            buf = tempBuf;
        } else {
            chear(buf);
            System.arraycopy(chars, 0, buf, 0, str.length());
        }
        len = str.length();
    }

    void chear(char[] buf) {
        Arrays.fill(buf, '\u0000');
    }
}

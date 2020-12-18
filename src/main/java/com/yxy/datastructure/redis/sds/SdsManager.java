package com.yxy.datastructure.redis.sds;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author XinYu Yang
 * @date 2020/12/18  3:11 下午
 */
public class SdsManager {

    static final byte SDS_TYPE_5 = 0;
    static final byte SDS_TYPE_8 = 1;
    static final byte SDS_TYPE_16 = 2;
    static final byte SDS_TYPE_32 = 3;
    static final byte SDS_TYPE_64 = 4;

    static final byte MAX = 4;
    static final byte MIN = 0;

    static final int SDS_TYPE_5_SIZE = 32;
    static final int SDS_TYPE_8_SIZE = 64;
    static final int SDS_TYPE_16_SIZE = 128;
    static final int SDS_TYPE_32_SIZE = 256;
    static final int SDS_TYPE_64_SIZE = 512;

    public static Map<Byte, Sds> map = new HashMap<Byte, Sds>();

    static {
        map.put(SDS_TYPE_5, new Sdshdr5());
        map.put(SDS_TYPE_8, new Sdshdr8());
        map.put(SDS_TYPE_16, new Sdshdr16());
        map.put(SDS_TYPE_32, new Sdshdr32());
        map.put(SDS_TYPE_64, new Sdshdr64());
    }

    public static Sds getSdsByType(byte type) {
        if (type > MAX || type < MIN) {
            throw new RuntimeException("type is not fond");
        }
        Sds sds;
        switch (type) {
            case SDS_TYPE_5:
                sds = new Sdshdr5();
                break;
            case SDS_TYPE_8:
                sds = new Sdshdr8();
                break;
            case SDS_TYPE_16:
                sds = new Sdshdr16();
                break;
            case SDS_TYPE_32:
                sds = new Sdshdr32();
                break;
            case SDS_TYPE_64:
                sds = new Sdshdr64();
                break;
            default:
                sds = new Sdshdr5();
        }
        return sds;
    }

    Sds getSds(String str) {
        int size = str.length();
        byte type = getType(size);
        Sds sds = map.get(type);
        sds.init(str);
        return sds;
    }

    public static byte getType(int size) {
        byte type;
        if (size < SDS_TYPE_5_SIZE) {
            type = SDS_TYPE_5;
        } else if (size < SDS_TYPE_8_SIZE) {
            type = SDS_TYPE_8;
        } else if (size < SDS_TYPE_16_SIZE) {
            type = SDS_TYPE_16;
        } else if (size < SDS_TYPE_32_SIZE) {
            type = SDS_TYPE_32;
        } else {
            type = SDS_TYPE_64;
        }
        return type;
    }

}

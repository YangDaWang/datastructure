package com.yxy.datastructure.normal.rbtree;

/**
 * @Author XinYu Yang
 * @date 2018/9/26  下午9:37
 */
public enum RedBlackTreeEnum {

    /**
     *
     */
    RED("RED"), BLACK("BLACK");

    private String value;

    RedBlackTreeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

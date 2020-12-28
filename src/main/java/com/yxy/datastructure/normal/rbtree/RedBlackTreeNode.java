package com.yxy.datastructure.normal.rbtree;

/**
 * @Author XinYu Yang
 * @date 2018/9/25  下午8:32
 */
public class RedBlackTreeNode {

    private Integer value;
    private RedBlackTreeNode parent;
    private RedBlackTreeNode left;
    private RedBlackTreeNode right;
    private String color = RedBlackTreeEnum.RED.getValue();

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public RedBlackTreeNode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public RedBlackTreeNode getParent() {
        return parent;
    }

    public void setParent(RedBlackTreeNode parent) {
        this.parent = parent;
    }

    public RedBlackTreeNode getLeft() {
        return left;
    }

    public void setLeft(RedBlackTreeNode left) {
        this.left = left;
    }

    public RedBlackTreeNode getRight() {
        return right;
    }

    public void setRight(RedBlackTreeNode right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        RedBlackTreeNode redBlackTreeNode = (RedBlackTreeNode)obj;
        return this.getValue().equals(redBlackTreeNode.getValue());
    }
}

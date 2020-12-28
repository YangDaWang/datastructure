package com.yxy.datastructure.normal.rbtree;

/**
 * @Author XinYu Yang
 * @date 2018/9/26  下午7:33
 */
public class RedBlackTree {
    private RedBlackTreeNode root;

    public RedBlackTree(RedBlackTreeNode root){
        this.root = root;
    }

    public RedBlackTreeNode getRoot() {
        return root;
    }

    public void setRoot(RedBlackTreeNode root) {
        this.root = root;
    }

    //左旋
    RedBlackTreeNode rotateLeft(RedBlackTreeNode node) {
        //将传入节点定为x，将x的右节点拿到设为y
        RedBlackTreeNode rightNode = node.getRight();
        //将y的左节点给x的右节点
        node.setRight(rightNode.getLeft());
        //如果y的左节点不为空，那么将x设为y的左节点的父亲节点
        if (rightNode.getLeft() != null) {
            rightNode.getLeft().setParent(node);
        }

        //---------------------
        //将x节点的父亲节点给y
        rightNode.setParent(node.getParent());
        //如果x的父亲节点为空，那么表示这个节点就是根节点
        if (node.getParent() == null) {
            this.root = rightNode;
        } else if (node == node.getParent().getLeft()) {
            //如果x是其父亲节点的左节点，那么该位置替换成y
            node.getParent().setLeft(rightNode);
        } else {
            //如果x是其父亲节点的右节点，那么该位置替换成y
            node.getParent().setRight(rightNode);
        }
        //y的左节点替换成x
        rightNode.setLeft(node);
        //x的父亲节点替换成y
        node.setParent(rightNode);
        //返回树
        return root;
    }

    //右旋
    RedBlackTreeNode rotateRight(RedBlackTreeNode node) {

        RedBlackTreeNode leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        if (leftNode.getRight() != null) {
            leftNode.getRight().setParent(node);
        }

        leftNode.setParent(node.getParent());

        if (node.getParent() == null) {
            this.root = leftNode;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(leftNode);
        } else {
            node.getParent().setRight(leftNode
            );
        }

        leftNode.setRight(node);
        node.setParent(leftNode);

        return root;
    }

    //获得该子树的的最大节点
    RedBlackTreeNode getMaxTreeNode(RedBlackTreeNode node) {
        RedBlackTreeNode temp = node;
        while (temp.getRight() != null) {
            temp = temp.getRight();
        }
        return temp;
    }

    //获得该子树的最小节点
    RedBlackTreeNode getMinTreeNode(RedBlackTreeNode node) {
        RedBlackTreeNode temp = node;
        while (temp.getLeft() != null) {
            temp = temp.getLeft();
        }
        return temp;
    }

    //获得后继节点,后继节点指的是中序遍历后的node节点的后继节点
    RedBlackTreeNode successor(RedBlackTreeNode node) {

        if (node.getRight() != null) {
            return getMaxTreeNode(node.getRight());
        }

        RedBlackTreeNode parent = node.getParent();

        while (node != null && parent.equals(node.getParent().getRight())) {
            node = parent;
            parent = node.getParent();
        }
        return parent;
    }

    RedBlackTreeNode insertNode(RedBlackTreeNode node) {

        RedBlackTreeNode temp = null;
        RedBlackTreeNode head = root;
        //找到对应的节点
        while (head != null) {
            temp = head;
            if (head.getValue() > node.getValue()) {
                head = head.getLeft();
            } else {
                head = head.getRight();
            }
        }
        //插入相应的位置
        node.setParent(temp);
        if (temp == null) {
            root = node;
        } else if (node.getValue() > temp.getValue()) {
            temp.setRight(node);
        } else {
            temp.setLeft(node);
        }


        node.setLeft(null);
        node.setRight(null);
        node.setColor(RedBlackTreeEnum.RED.getValue());

        //插入调整红黑树重新着色代码
        insertAdjust(node);
        return root;
    }

    private void insertAdjust(RedBlackTreeNode node) {

        while (RedBlackTreeEnum.BLACK.getValue().equals(node.getParent().getColor())) {
            //判断x的父亲节点是x的祖父节点的左节点还是右节点
            if (node.getParent().equals(node.getParent().getParent().getLeft())) {
                RedBlackTreeNode uncleNode = node.getParent().getParent().getRight();
                //情况3，当前节点的父亲节点是红色，叔叔节点也是红色
                if (uncleNode.getColor().equals(RedBlackTreeEnum.RED.getValue())) {
                    //解决方案：把x的父亲叔叔节点涂成黑色，把祖父节点涂成红色，把祖父节点制成当前节点
                    uncleNode.setColor(RedBlackTreeEnum.BLACK.getValue());
                    node.getParent().setColor(RedBlackTreeEnum.BLACK.getValue());
                    node.getParent().getParent().setColor(RedBlackTreeEnum.RED.getValue());
                    node = node.getParent().getParent();
                    //情况4，当前节点的父亲节点是红色，叔叔节点是黑色，当前节点是父亲节点的左子
                } else if (node.getParent().getLeft().equals(node)) {
                    //解决方案：把当前节点的父亲节点当成当前节点，左旋
                    node = node.getParent();
                    rotateLeft(node);
                }
                //情况5：当前节点的父亲节点是红色，叔叔节点是黑色，当前节点是父亲节点的右子
                //解决方案：把父亲节点涂黑，把祖父节点涂红,当前节点为支点右旋
                node.getParent().setColor(RedBlackTreeEnum.BLACK.getValue());
                node.getParent().getParent().setColor(RedBlackTreeEnum.RED.getValue());
                rotateRight(node);
            } else {
                //x的父亲节点是祖父节点的右节点的情况
                RedBlackTreeNode uncleNode = node.getParent().getParent().getRight();
                //思路是一样的,首先判断当前节点的父亲节点是不是红色的，如果是黑色的则直接插入不会破坏红黑树的性质，
                //如果是红色的，那看叔叔节点是是不是红色，如果是红色的，就是情况3
                if (uncleNode.getColor().equals(RedBlackTreeEnum.RED.getValue())) {
                    //情况3：父亲节点和叔叔节点都是红色的
                    node.getParent().setColor(RedBlackTreeEnum.BLACK.getValue());
                    uncleNode.setColor(RedBlackTreeEnum.BLACK.getValue());
                    node.getParent().getParent().setColor(RedBlackTreeEnum.RED.getValue());
                    node = node.getParent().getParent();
                } else if (node.getParent().getLeft().equals(node)) {
                    node = node.getParent();
                    rotateLeft(node);
                }
                node.getParent().setColor(RedBlackTreeEnum.BLACK.getValue());
                node.getParent().getParent().setColor(RedBlackTreeEnum.RED.getValue());
                rotateRight(node);
            }
        }
        root.setColor(RedBlackTreeEnum.BLACK.getValue());
    }

    RedBlackTreeNode deleteNode(RedBlackTreeNode node) {

        if (root.equals(node) && node.getRight() == null && node.getLeft() == null) {
            return null;
        }

        //deleteNode为待删除节点
        RedBlackTreeNode deletedNode;

        //如果node不是左右节点都存在的情况下，待删除节点就是传入节点
        if (node.getRight() == null || node.getLeft() == null) {
            deletedNode = node;
        } else {
            //左右节点都存在，那么待删除节点就是传入节点的后继节点
            deletedNode = successor(node);
        }

        //待删除节点的子节点，这里一定只有一个节点或没有节点
        RedBlackTreeNode childNode;
        if (deletedNode.getLeft() != null) {
            childNode = deletedNode.getLeft();
        } else {
            childNode = deletedNode.getRight();
        }

        //把孩子节点的父亲节点指向待删除节点的父亲节点
        if (childNode != null) {
            childNode.setParent(deletedNode.getParent());
        }

        //如果待删除节点的父亲节点为空
        if (deletedNode.getParent() == null) {
            //表示待删除节点的父亲节点就是根节点
            root = childNode;
        } else if (deletedNode.getParent().getRight().equals(deletedNode)) {
            //不为空就把左右节点置成待删除节点的左右节点
            deletedNode.getParent().setRight(childNode);
        } else {
            deletedNode.getParent().setLeft(childNode);
        }

        //将待删除节点的值给node
        if (deletedNode.equals(node)) {
            node.setValue(deletedNode.getValue());
        }
        //如果待删除节点的替代节点的颜色为黑色，则需要调整红黑树
        if (deletedNode.getColor().equals(RedBlackTreeEnum.BLACK.getValue())) {
            deleteAdjust(childNode);
        }

        return deletedNode;
    }

    private void deleteAdjust(RedBlackTreeNode node) {
        while (node != null && RedBlackTreeEnum.BLACK.getValue().equals(node.getColor())) {
            //当前节点是父亲节点的左节点
            if (node.getParent().getLeft().equals(node)) {
                RedBlackTreeNode right = node.getParent().getRight();
                //情况3当前节点为黑色，兄弟节点为红色
                if (RedBlackTreeEnum.RED.getValue().equals(right.getColor())) {
                    //解决方法：把父亲节点涂成红色，兄弟节点涂成黑色，以当前节点的父亲节点为支轴，左旋,重置兄弟节点
                    node.getParent().setColor(RedBlackTreeEnum.RED.getValue());
                    right.setColor(RedBlackTreeEnum.BLACK.getValue());
                    rotateLeft(node.getParent());
                    right = node.getParent().getRight();
                }
                //情况4当前节点为黑色，兄弟节点为黑色，兄弟节点的两个子节点都是黑色
                if (RedBlackTreeEnum.BLACK.getValue().equals(right.getLeft()) && RedBlackTreeEnum.BLACK.getValue().equals(right.getRight())) {
                    //解决方法：将兄弟节点涂红，将父亲节点置成当前节点
                    right.setColor(RedBlackTreeEnum.RED.getValue());
                    node = node.getParent();
                    //情况5当前节点为黑色，兄弟节点的右子黑，左子红
                } else if (RedBlackTreeEnum.BLACK.getValue().equals(right.getRight().getColor())) {
                    //解决方法：将兄弟节点置为红，兄弟节点的左子置黑，兄弟节点为支点右旋
                    right.setColor(RedBlackTreeEnum.RED.getValue());
                    right.getLeft().setColor(RedBlackTreeEnum.BLACK.getValue());
                    rotateRight(right);
                    right = node.getParent().getRight();
                }
                //情况6：兄弟节点为黑，兄弟右子红，左子
                right.setColor(RedBlackTreeEnum.RED.getValue());
                node.getParent().setColor(RedBlackTreeEnum.BLACK.getValue());
                right.getRight().setColor(RedBlackTreeEnum.BLACK.getValue());
                rotateLeft(node);
                node = root;
            } else {
                RedBlackTreeNode left = node.getParent().getLeft();
                if (RedBlackTreeEnum.RED.getValue().equals(left.getColor())) {
                    node.getParent().setColor(RedBlackTreeEnum.RED.getValue());
                    left.setColor(RedBlackTreeEnum.BLACK.getValue());
                    rotateRight(node.getParent());
                    left = node.getParent().getLeft();
                }
                if (RedBlackTreeEnum.BLACK.getValue().equals(left.getLeft()) && RedBlackTreeEnum.BLACK.getValue().equals(left.getRight())) {
                    left.setColor(RedBlackTreeEnum.BLACK.getValue());
                    node = node.getParent();
                } else if (RedBlackTreeEnum.BLACK.getValue().equals(left.getLeft().getColor())) {
                    left.setColor(RedBlackTreeEnum.RED.getValue());
                    left.getRight().setColor(RedBlackTreeEnum.BLACK.getValue());
                    rotateLeft(left);
                    left = node.getParent().getLeft();
                }
                left.setColor(RedBlackTreeEnum.RED.getValue());
                node.getParent().setColor(RedBlackTreeEnum.BLACK.getValue());
                left.getLeft().setColor(RedBlackTreeEnum.BLACK.getValue());
                rotateRight(node);
                node = root;
            }
        }
        node.setColor(RedBlackTreeEnum.BLACK.getValue());
    }

}

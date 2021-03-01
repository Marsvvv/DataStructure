package BinarySearchTree;

import BinaryTree.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树： 对于树中的每个节点X，它的左子树中所有项的值都小于X中的项，而它的右子树中所有项的值都大于X中的项
 * 有时也称二叉排序树 Binary Sort Tree，但是他们的定义是一样的
 * <p>
 * 平均深度 log N
 *
 * @author asus
 */
public class BinarySearchTree {

    private TreeNode node;

    public void makeEmpty() {
        node = null;
    }

    public boolean isEmpty() {
        return null == node;
    }

    public boolean contains(Integer ele) {
        return contains(ele, node);
    }

    /**
     * 1.利用二叉搜索树节点与子树之间的大小关系进行搜索(大于查右子树，小于查左子树，等于返回true，null返回false)
     * 2.采用递归方式进行深度查询
     *
     * @param ele  输入数据
     * @param node 父节点
     * @return 是否存在
     */
    private boolean contains(Integer ele, TreeNode node) {
        //  如果节点为空，则不包含
        if (isEmpty()) {
            return false;
        }
        int result = node.compareTo(ele);

        //  如果等于0，则说明输入数据等于当前二叉树中的数据
        if (result == 0) {
            return true;
        } else if (result > 0) {
            return contains(ele, node.right);
        } else {
            return contains(ele, node.left);
        }
    }

    public Integer findMin() {
        return findMin(node).element;
    }

    /**
     * 寻找最小的数据也就是查找最左边的子树
     *
     * @param node 父节点
     * @return 子节点
     */
    private TreeNode findMin(TreeNode node) {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        if (node.left == null) {
            return node;
        }
        return findMin(node.left);
    }

    public Integer findMax() {
        return findMax(node).element;
    }

    private TreeNode findMax(TreeNode node) {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        if (node.right == null) {
            return node;
        }
        return findMin(node.right);
    }

    public void insert(Integer ele) {
        node = insert(ele, node);
    }

    private TreeNode insert(Integer ele, TreeNode node) {
        //  用于插入
        if (null == node) {
            return new TreeNode(ele);
        }

        //  判断元素与当前节点的大小
        int result = node.element.compareTo(ele);

        //  这里需要重新赋值时因为当找到适合的空节点进行插入后，我们的数据需要被重新赋值到上一个父节点
        if (result < 0) {
            node.right = insert(ele, node.right);
        } else if (result > 0) {
            node.left = insert(ele, node.left);
        }
        return node;
    }

    /**
     * 删除节点有两种方式：
     * 1.惰性删除：不对节点进行删除，只是在节点上增加一个删除的标识，适用于少量的删除
     * 2.直接删除：删除节点，但是删除节点效率不高，因为可能要重新排布树结构
     * <p>
     * 删除节点有三种情况：
     * 1.当前节点没有子节点       直接删除
     * 2.当前节点只有一个子节点    删除后直接对子节点移动
     * 3.当前节点有两个子节点
     *
     * @param ele  数据
     * @param node 父节点
     * @return 删除后的节点
     *//*
    private TreeNode remove(Integer ele, TreeNode node) {
        try {
            TreeNode child = getNode(ele, node);

            //  找到该子节点的父节点
            TreeNode parentNode = getParentNode(child, node);

            //  第一种情况，没有任何子节点，直接删除
            if (null == child.left && null == child.right) {
                //  判断该子节点在父节点的哪一边
                if (parentNode.left == child) {
                    //  将原来该子节点的位置置空
                    parentNode.left = null;
                } else {
                    parentNode.right = null;
                }
            }

            //  第二种情况，有一个子节点，建立父节点与孙节点的关系
            if ((null == child.left && null != child.right)
                    || (null == child.right && null != child.left)) {
                //  判断该子节点在父节点的哪一边
                if (parentNode.left == child) {
                    //  将原来该子节点的位置置空
                    parentNode.left = null;
                } else {
                    parentNode.right = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
    public void removeMinNode() {
        node = removeMinNode(node);
    }

    /**
     * 删除给定节点中最小的节点
     * 实现方式与删除最大节点有一点点不同，但是意思差不多
     * 1.如果左节点右节点都为空，那么直接返回null，父节点会将上个节点置为null
     * 2.如果有右节点，那么返回右节点，父节点连接剩余节点
     *
     * @param node 根节点
     * @return 最小的节点
     */
    private TreeNode removeMinNode(TreeNode node) {
        //  1.递归结束条件
        if (node.left == null) {
            //  2.判断当前节点是否拥有右节点
            if (node.right == null) {
                //  3.叶子节点只需要返回null，这样使得上个节点与之连接
                return null;
            } else {
                //  4.当前节点拥有右节点，则将右节点与上个节点连接
                return node.right;
            }
        }

        node.left = removeMinNode(node.left);
        return node;
    }

    public void removeMaxNode() {
        node = removeMaxNode(node);
    }

    /**
     * 删除给定节点中最大的节点
     * 此实现方式和最小节点类似，有一点小小的区别
     * 因为我要删除最大的节点，所以最大的节点是没有右子树的，那么此节点只会有两种情况：
     * 1.此节点有左子树
     * 2.此节点为叶子节点(此节点没有左子树)
     * 不管有没有左子树，我都取出左子树，如果左子树为空我取出的是null，并不影响之后的代码逻辑，因为如果左子树本来就为空，那么返回null没有问题
     * 比较关键的就是递归的时候要返回给父级的节点
     * 最后的return node非常关键，如果没有到递归结束条件，那么我继续查找，我返回当前的深度为1的节点
     *
     * @param node 根节点
     * @return 最大的节点
     */
    private TreeNode removeMaxNode(TreeNode node) {
        //  1.递归结束条件
        if (node.right == null) {
            //  2.获取node的左子树，即使为null也不影响逻辑
            TreeNode left = node.left;
            //  3.将node的左子树置空
            node.left = null;
            //  4.返回左子树值
            return left;
        }

        node.right = removeMaxNode(node.right);
        return node;
    }

    public void remove(Integer e) {
        node = remove(node, e);
    }

    /**
     * 删除节点有3种情况：
     * 1.删除的节点只有左孩子，将删除节点的父节点的删除节点的位置设置为删除节点的左孩子
     * 2.删除的节点只有右孩子，将删除节点的父节点的删除节点的位置设置为删除节点的右孩子
     * 3.删除的节点有两个孩子（Hibbard Deletion），寻找后继节点，删除节点的右节点的最小左孩子设置为删除节点的位置
     * 也可以查找前驱节点，也就是删除节点的左节点的最大节点
     * 4.删除的节点没有孩子
     *
     * @param node
     * @param e
     * @return
     */
    private TreeNode remove(TreeNode node, Integer e) {
        //  先找到要删除的节点
        if (node.element.equals(e)) {
            if (node.right == null) {
                //  左孩子是否为空不需要判断，为空直接为null也不影响逻辑
                //  只有左孩子的情况
                return node.left;
            }

            if (node.left == null) {
                return node.right;
            }

            //  查找当前节点右节点的最小节点 --- 后继节点
            TreeNode min = findMin(node.right);
            //  后继节点连接删除节点的左右子节点
            min.left = node.left;
            //  删除后继节点的位置
            min.right = removeMinNode(node.right);
            //  返回后继节点
            return min;
        }


        //  分情况进行递归
        if (node.element.compareTo(e) > 0) {
            node.left = remove(node.left, e);
        } else if (node.element.compareTo(e) < 0) {
            node.right = remove(node.right, e);
        }
        return node;
    }

    /**
     * 层序遍历
     * 层序遍历使用队列进行实现，不使用递归
     * 将节点放入队列中，先进先出，队列的处理流程就是如果左或右孩子不为null，那么就将左、右孩子加入队列中
     * 直到取出队列中所有的节点
     */
    public void levelOrder() {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            //  从队列中拿出第一个元素
            TreeNode node = queue.remove();
            //  判断元素左右节点是否为空
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            System.out.println(node.element);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(8);
        bst.insert(3);
        bst.insert(1);
        bst.insert(10);
        bst.insert(6);
        bst.levelOrder();
    }

    private TreeNode getNode(Integer ele, TreeNode node) throws Exception {
        if (isEmpty()) {
            throw new Exception("二叉搜索树中不存在此节点");
        }

        int result = node.compareTo(ele);

        if (result == 0) {
            return node;
        } else if (result > 0) {
            return getNode(ele, node.right);
        } else {
            return getNode(ele, node.left);
        }
    }

    private TreeNode getParentNode(TreeNode child, TreeNode node) throws IllegalAccessException {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        if (node.left == child || node.right == child) {
            return node;
        }

        int result = node.compareTo(child.element);

        if (result > 0) {
            return getParentNode(child, node.left);
        } else if (result < 0) {
            return getParentNode(child, node.right);
        } else {
            throw new IllegalAccessException();
        }
    }

    public void printTree(Integer ele) {

    }

    private void printTree(TreeNode node) {

    }

    /**
     * 由于需要比较项与项之间的值来确定在树中的位置，所以需要实现Comparable接口
     */
    class TreeNode implements Comparable<Integer> {

        private Integer element;

        private TreeNode left;

        private TreeNode right;

        public TreeNode(Integer element) {
            this.element = element;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        @Override
        public int compareTo(Integer o) {
            return element - o;
        }
    }
}

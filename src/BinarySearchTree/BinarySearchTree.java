package BinarySearchTree;

/**
 * 二叉搜索树： 对于树中的每个节点X，它的左子树中所有项的值都小于X中的项，而它的右子树中所有项的值都大于X中的项
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
        int result = node.compareTo(ele);

        //  这里需要重新赋值时因为当找到适合的空节点进行插入后，我们的数据需要被重新赋值到上一个父节点
        if (result > 0) {
            node.right = insert(ele, node.right);
        } else if (result < 0) {
            node.left = insert(ele, node.left);
        }
        return node;
    }

    public void remove(Integer ele) {
        remove(ele, node);
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
     */
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

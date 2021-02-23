package AVL;

/**
 * 平衡二叉搜索树：
 * 1.它是一棵空树或它的左右子树的高度差的绝对值不超过1
 * 2.它的左右子树都是一颗平衡二叉树
 * <p>
 * 平衡因子(Balance Factor, BF)：
 * 某节点的左子树和右子树的高度差
 * <p>
 * 在平衡二叉搜索树中，所有节点的平衡因子只可能是 -1、0、1
 */
public class AVL {

    private class Node {
        /**
         * 节点数据
         */
        private Integer element;
        /**
         * 左节点
         */
        private Node left;
        /**
         * 右节点
         */
        private Node right;

        /**
         * 当前节点的高度
         */
        private Integer height;

        public Node(Integer element, Node left, Node right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 1;
        }
    }

    private Node root;

    private Integer size;

    public AVL() {
        root = null;
        size = 0;
    }

    /**
     * 获取某个节点的高度
     *
     * @param node 节点
     * @return 高度
     */
    public int getHeight(Node node) {
        if (null == node) {
            return 0;
        }
        return node.height;
    }

    /**
     * 获取平衡二叉树的大小
     *
     * @return 平衡二叉树的大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 平衡二叉树是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 获取当前节点的平衡因子
     * 平衡因子(Balance Factor, BF)：某节点的左子树和右子树的高度差
     *
     * @param node 节点
     * @return 平衡因子
     */
    public Integer getBalanceFactor(Node node) {
        if (null == node) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public boolean isBalance() {
        return isBalance(root);
    }

    /**
     * 判断此节点下的树是否为平衡二叉树
     * 1.它是一棵空树或它的左右子树的高度差的绝对值不超过1
     * 2.它的左右子树都是一颗平衡二叉树
     *
     * @param node 节点
     * @return 是否为平衡二叉树
     */
    private boolean isBalance(Node node) {
        if (null == node) {
            return true;
        }
        int bf = Math.abs(getBalanceFactor(node));
        if (bf > 1) {
            return false;
        }
        return isBalance(node.left) && isBalance(node.right);
    }

    /**
     * LL: 向 左子树(L) 的 左孩子(L) 中插入新节点后导致不平衡
     * 这种情况下需要右旋操作
     *
     *       y               x
     *      / \            /   \
     *     x   t4         z     y
     *    / \            / \   / \
     *   z   t3         t1 t2 t3 t4
     *  / \
     * t1  t2
     *
     * @param y 节点树
     * @return 新节点树
     */
    private Node LL(Node y) {
        Node x = y.left;
        Node t3 = x.right;
        x.right = y;
        y.left = t3;
        //  更新节点高度
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * RR: 向 右子树(R) 的 右孩子(R) 中插入新节点后导致不平衡
     * 这种情况下需要左旋操作
     *
     *       y                 x
     *      / \              /   \
     *     t4  x            y     z
     *        / \          / \   / \
     *       t3  z        t4 t3 t2 t1
     *          / \
     *         t2  t1
     *
     * @param y 节点树
     * @return 新节点树
     */
    private Node RR(Node y) {
        Node x = y.right;
        Node t3 = x.left;
        x.left = y;
        y.right = t3;
        //  更新节点高度
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * LR: 向 左子树(L) 的 右孩子(R) 中插入新节点后导致不平衡
     * 这种情况下需要 先x左旋 再y右旋
     *
     *        y            y             z
     *       / \          / \          /   \
     *      x  t4        z  t4        x     y
     *     / \          / \          / \   / \
     *    t1  z        x  t3        t1 t2 t3 t4
     *       / \      / \
     *      t2 t3    t1 t2
     * @param y 节点树
     * @return 新节点树
     */
    private Node LR(Node y) {
        //  以x为节点左旋
        Node x = y.left;
        Node z = x.right;
        Node t2 = z.left;
        y.left = z;
        z.left = x;
        x.right = t2;
        //  以y为节点右旋
        Node t3 = z.right;
        z.right = y;
        y.left = t3;
        //  更新节点高度
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        z.height = Math.max(getHeight(z.left), getHeight(z.right)) + 1;
        //  返回新的根节点
        return z;
    }

    /**
     * RL: 向 右子树(R) 的 左孩子(L) 中插入新节点后导致不平衡
     * 这种情况下需要 先x右旋 再y左旋
     *
     *         y               y                z
     *        / \             / \             /   \
     *       t1  x           t1  z           y     x
     *          / \             / \         / \   / \
     *         z  t4           t2  x       t1 t2 t3 t4
     *        / \                 / \
     *       t2 t3               t3 t4
     *
     * @param y
     * @return
     */
    private Node RL(Node y) {
        //  以x为节点右旋
        Node x = y.right;
        Node z = x.left;
        Node t3 = z.right;
        y.right = z;
        z.right = x;
        x.left = t3;
        //  以y为节点左旋
        Node t2 = z.left;
        z.left = y;
        y.right = t2;
        //  更新节点高度
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        z.height = Math.max(getHeight(z.left), getHeight(z.right)) + 1;
        //  返回新的根节点
        return z;
    }

    public void add(Integer e) {
        root = add(root, e);
    }

    private Node add(Node node, Integer e) {
        //  节点为空，插入元素
        if(null == node) {
            size ++;
            return new Node(e, null, null);
        }
        //  节点不为空，递归添加元素
        if(e.compareTo(node.element) > 0) {
            //  比当前节点大
            node.right = add(node.right, e);
        } else if(e.compareTo(node.element) < 0) {
            //  比当前节点小
            node.left = add(node.left, e);
        }
        //  更新高度
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        //  计算平衡因子
        int bf = getBalanceFactor(node);
        //  当前节点左子树不平衡且当前节点的左子树的平衡因子大于0，说明是LL，右旋
        if(bf > 1 && getBalanceFactor(node.left) > 0) {
            return LL(node);
        }
        if(bf > 1 && getBalanceFactor(node.left) < 0) {
            return LR(node);
        }
        if(bf < -1 && getBalanceFactor(node.right) > 0) {
            return RR(node);
        }
        if(bf < -1 && getBalanceFactor(node.right) < 0) {
            return RL(node);
        }
        return node;
    }
}

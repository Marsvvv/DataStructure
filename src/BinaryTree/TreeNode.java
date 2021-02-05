package BinaryTree;

/**
 * 二叉树 Binary Tree： 每个节点都不能多于两个孩子
 * <p>
 * 性质：
 * 1.每个节点都不能多于两个孩子
 * 2.一颗二叉树的平均深度要比节点个数小得多，平均深度为 根号n，最坏情形的二叉树深度为N-1（像链表一样）
 */
public class TreeNode {

    /**
     * 此树所保存的数据
     */
    private Object element;

    /**
     * 左子树
     */
    private TreeNode left;

    /**
     * 右子树
     */
    private TreeNode right;

    public TreeNode(Object element) {
        this.element = element;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    static class Traverse {

        /**
         * 二叉树 先序、中序、后序遍历中的先、中、后形容的是根所应该遍历的位置
         * 先序就是先遍历根，后序就是在最后遍历根
         * 默认的顺序是左子树优先于右子树遍历
         *
         * 前序、中序、后续遍历均使用递归的方式实现
         */

        /**
         * 中序遍历
         * left - node - right (左 根 右)
         *
         * @param node 节点
         */
        public static void LDR(TreeNode node) {

            if (node == null) {
                return;
            }
            if (null != node.left) {
                System.out.print("(");
            }
            LDR(node.left);
            System.out.print(node.element);
            LDR(node.right);
            if (null != node.right) {
                System.out.print(")");
            }
        }

        /**
         * 先序遍历
         * node - left - right(根 - 左 - 右)
         *
         * @param node 节点
         */
        public static void DLR(TreeNode node) {

            if (null == node) {
                return;
            }
            System.out.print(node.element);
            DLR(node.left);
            DLR(node.right);

        }

        /**
         * 后序遍历
         * left - right - node(左 - 右 - 根)
         *
         * @param node 节点
         */
        public static void LRD(TreeNode node) {

            if (null == node) {
                return;
            }
            LRD(node.left);
            LRD(node.right);
            System.out.print(node.element);
        }
    }

    static class ExpressionTree {

        public static void main(String[] args) {

            //  左子树开始
            TreeNode muti = new TreeNode('*');
            TreeNode b = new TreeNode('b');
            TreeNode c = new TreeNode('c');
            muti.setLeft(b);
            muti.setRight(c);

            TreeNode add = new TreeNode("+");
            TreeNode a = new TreeNode('a');
            add.setLeft(a);
            add.setRight(muti);
            //  左子树完成

            //  右子树开始
            TreeNode muti2 = new TreeNode('*');
            TreeNode d = new TreeNode('d');
            TreeNode e = new TreeNode('e');
            muti2.setLeft(d);
            muti2.setRight(e);

            TreeNode add2 = new TreeNode("+");
            TreeNode f = new TreeNode('f');
            add2.setLeft(muti2);
            add2.setRight(f);

            TreeNode muti3 = new TreeNode('*');
            TreeNode g = new TreeNode('g');
            muti3.setLeft(add2);
            muti3.setRight(g);
            //  右子树完成

            //  根构建开始
            TreeNode root = new TreeNode("+");
            root.setLeft(add);
            root.setRight(muti3);
            //  根构建完成

            //  中序遍历
            System.out.print("中序遍历： ");
            Traverse.LDR(root);

            //  前序遍历
            System.out.println("");
            System.out.print("前序遍历： ");
            Traverse.DLR(root);

            //  后序遍历
            System.out.println("");
            System.out.print("后序遍历： ");
            Traverse.LRD(root);
        }
    }
}
